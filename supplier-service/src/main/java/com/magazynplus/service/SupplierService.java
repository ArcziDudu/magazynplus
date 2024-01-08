package com.magazynplus.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.dto.SupplierResponse;
import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.entity.SupplierEntity;
import com.magazynplus.exception.ProductNotFoundException;
import com.magazynplus.mapper.UserMapper;
import com.magazynplus.repository.SupplierRepository;
import com.magazynplus.repository.mapper.SupplierMapper;
import com.magazynplus.webClientService.UserInfoFetcherImpl;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final ObservationRegistry observationRegistry;
    private final UserMapper userMapper;
    private final UserInfoFetcherImpl userInfoFetcher;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private String username;
    private String jwtToken;

    @KafkaListener(topics = "login")
    public void handleLoginNotification(String receivedUsername) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Receive kafka topic: " + receivedUsername);
            username = receivedUsername;
        });
    }
    @KafkaListener(topics = "token")
    public void handleToken(String receivedToken) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            jwtToken = receivedToken;
        });
    }
    @KafkaListener(topics = "suppliersFromFile")
    @Transactional
    public void handleSuppliersFromFileToSave(String receivedSuppliers) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Receive kafka topic: " + "suppliersFromFile");
            try {
                List<SupplierEntity> suppliersList
                        = Arrays.asList(objectMapper.readValue(receivedSuppliers, SupplierEntity[].class));
                UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken, username);
                try{
                    suppliersList.forEach(a->a.setUser(userMapper.mapFromRequest(userResponse)));
                    supplierRepository.saveAll(suppliersList);
                    log.info("Suppliers from file has been saved successfully");
                }catch (DataIntegrityViolationException | ConstraintViolationException ex) {
                    kafkaTemplate.send("fileResponse", "error");

                }

            } catch (JsonProcessingException e) {
                kafkaTemplate.send("fileResponse", "error");
                throw new RuntimeException(e);
            }
        });
    }
    @Transactional
    public SupplierResponse createSupplier(SupplierRequest request, String jwtToken) {
        UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken, username);
        SupplierEntity supplierEntity = supplierMapper.mapFromRequest(request);
        supplierEntity.setUser(userMapper.mapFromRequest(userResponse));
        return supplierMapper.mapFromEntity(supplierRepository.save(supplierEntity));
    }

    public List<SupplierResponse> findAllByUser(String jwtToken) {
        UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken, username);
        return supplierRepository.findAllByUserId(userResponse.id()).stream()
                .map(supplierMapper::mapFromEntity).toList();
    }

    @Transactional
    public void deleteById(Integer supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new ProductNotFoundException(
                    String.format("Supplier with id [%s] does not exists!", supplierId));
        } else {
            supplierRepository.deleteById(supplierId);
            log.info("Product with id {} has been removed", supplierId);
        }
    }

    @Transactional
    public SupplierResponse editSupplier(SupplierRequest request) {
        SupplierEntity supplier = supplierRepository.findById(request.id())
                .orElseThrow(() ->
                        new RuntimeException(String.format("Supplier with id [%s] does not exists!", request.id())));
        supplier.setName(request.name());
        supplier.setEmail(request.email());
        supplier.setNip(request.nip());
        supplier.setAddress(request.address());
        supplier.setPhoneNumber(request.phoneNumber());
        supplier.setPostalCode(request.postalCode());
        SupplierResponse supplierResponse = supplierMapper.mapFromEntity(supplierRepository.save(supplier));
        log.info("Supplier with id {} has been edit successfully", request.id());
        return supplierResponse;
    }

    public SupplierResponse getDetailsById(Integer supplierId) {
        return supplierMapper.mapFromEntity(supplierRepository.findById(supplierId).orElseThrow(() ->
                new RuntimeException(String.format("Supplier with id [%s] does not exists!", supplierId))));
    }

}
