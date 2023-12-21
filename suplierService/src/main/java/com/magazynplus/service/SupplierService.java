package com.magazynplus.service;


import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.dto.SupplierResponse;
import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.SupplierEntity;
import com.magazynplus.exception.ProductNotFoundException;
import com.magazynplus.mapper.UserMapper;
import com.magazynplus.repository.SupplierRepository;
import com.magazynplus.repository.mapper.SupplierMapper;
import com.magazynplus.webClientService.UserInfoFetcherImpl;
import io.micrometer.observation.ObservationRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObservationRegistry observationRegistry;
    private final UserMapper userMapper;
    private final UserInfoFetcherImpl userInfoFetcher;

    @Transactional
    public SupplierResponse createSupplier(SupplierRequest request, String jwtToken) {
        UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken);
        SupplierEntity supplierEntity = supplierMapper.mapFromRequest(request);
        supplierEntity.setUser(userMapper.mapFromRequest(userResponse));
        return supplierMapper.mapFromEntity(supplierRepository.save(supplierEntity));

//        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
//                this.observationRegistry);
//        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
//        inventoryServiceObservation.observe(() -> {
//            supplierRepository.save(supplierEntity);
//            applicationEventPublisher.publishEvent(new NewSupplierEvent(this, supplierEntity));
//
//        });
    }

    public List<SupplierResponse> findAllByUser() {
        return supplierRepository.findAllByUserId(2).stream()
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
