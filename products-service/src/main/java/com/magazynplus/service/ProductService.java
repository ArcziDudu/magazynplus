package com.magazynplus.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazynplus.dto.ProductRequest;
import com.magazynplus.dto.ProductResponse;
import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.exception.ProductNotFoundException;
import com.magazynplus.mapper.ProductMapper;
import com.magazynplus.mapper.UserMapper;
import com.magazynplus.repository.ProductJpaRepository;
import com.magazynplus.webClientService.UserInfoFetcherImpl;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final UserInfoFetcherImpl userInfoFetcher;
    private final ObservationRegistry observationRegistry;
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

    @KafkaListener(topics = "fileProcessed")
    @Transactional
    public void handleProductsFromFileToSave(String receivedProducts) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Receive kafka topic: " + "fileProcessed");
            try {
                List<ProductEntity> productList
                        = Arrays.asList(objectMapper.readValue(receivedProducts, ProductEntity[].class));
                UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken, username);
                List<ProductEntity> productsByUserId = productJpaRepository.findByUserId(userResponse.id());

                if (productsByUserId.isEmpty()) {
                    productList.forEach(a -> a.setUser(userMapper.mapFromRequest(userResponse)));
                    try {
                        productJpaRepository.saveAll(productList);
                        kafkaTemplate.send("fileResponse", "success");
                    } catch (DataIntegrityViolationException | ConstraintViolationException ex) {
                        kafkaTemplate.send("fileResponse", "error");
                        log.info("products from file has been saved successfully");
                    }
                } else {

                    for (ProductEntity productEntity : productList) {

                        if (productsByUserId.contains(productEntity)) {

                            ProductEntity existingProduct = productsByUserId.stream()
                                    .filter(p -> p.equals(productEntity))
                                    .findFirst()
                                    .orElse(null);
                            if (existingProduct != null) {

                                existingProduct.setQuantity(existingProduct.getQuantity() + productEntity.getQuantity());
                                productJpaRepository.save(existingProduct);
                                log.info("Product quantity updated successfully");
                            }
                        } else {
                            productEntity.setUser(userMapper.mapFromRequest(userResponse));
                            productJpaRepository.save(productEntity);
                            log.info("New product has been created successfully");
                        }
                    }
                    kafkaTemplate.send("fileResponse", "success");
                }
            } catch (JsonProcessingException e) {
                kafkaTemplate.send("fileResponse", "error");
                throw new RuntimeException(e);
            }
        });
    }

    @Transactional
    public ResponseEntity<HttpStatus> saveNewProduct(ProductRequest productRequest, String jwtToken) {

        UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken, username);
        List<ProductEntity> productsByUserId = productJpaRepository.findByUserId(userResponse.id());
        ProductEntity productEntity = productMapper.mapFromRequest(productRequest);

        if (productsByUserId.contains(productEntity)) {

            log.info("Found the same product");

            ProductEntity existingProduct = productsByUserId.stream()
                    .filter(p -> p.equals(productEntity))
                    .findFirst()
                    .orElse(null);

            if (existingProduct != null) {
                existingProduct.setQuantity(existingProduct.getQuantity() + productEntity.getQuantity());
                productJpaRepository.save(existingProduct);
                log.info("Product quantity updated successfully");
                return ResponseEntity.ok(HttpStatus.NO_CONTENT);
            }
        } else {
            ProductEntity uniqueProduct = productMapper.mapFromRequest(productRequest);
            uniqueProduct.setUser(userMapper.mapFromRequest(userResponse));
            ProductEntity save = productJpaRepository.save((uniqueProduct));
            productMapper.mapFromEntity(save);
            log.info("New product created successfully");
            return ResponseEntity.ok(HttpStatus.OK);
        }

        throw new RuntimeException("An error occurred while saving the product");
    }


    @Transactional
    public void deleteProductById(Long productId) {

        if (!productJpaRepository.existsById(productId)) {
            throw new ProductNotFoundException(
                    String.format("Product with id [%s] does not exists!", productId));
        } else {
            productJpaRepository.deleteById(productId);
            log.info("Product with id {} has been removed", productId);
        }
    }

    public List<ProductResponse> findBySearchKey(String searchKey, String jwtToken) {
        UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken, username);
        return productJpaRepository.findByUserIdAndNameContaining(userResponse.id(), searchKey)
                .stream()
                .map(productMapper::mapFromEntity).toList();
    }

    public List<ProductResponse> findAllByUser(Integer page, String jwtToken) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken, username);
        return productJpaRepository.findByUserId(userResponse.id(), pageable)
                .stream()
                .map(productMapper::mapFromEntity).toList();
    }

    //if product with the same name and best before date exists, only update amount of product
    private boolean existsByBestBeforeDateAndNameAndUserId(ProductRequest request) {
        return productJpaRepository.existsByBestBeforeDateAndNameAndUserId(request.bestBeforeDate(), request.name(), 2);
    }


    public ProductResponse getProductDetails(Long productId) {
        Optional<ProductEntity> productById = productJpaRepository.findById(productId);
        if (productById.isEmpty()) {
            throw new ProductNotFoundException(
                    String.format("Product with id [%s] does not exists!", productId));
        } else {
            return productMapper.mapFromEntity(productById.get());
        }
    }

    @Transactional
    public ProductResponse editProduct(ProductRequest productRequest) {
        ProductEntity productEntity = productJpaRepository.findById(productRequest.id()).orElseThrow(()
                -> new ProductNotFoundException(String.format("Product with id [%s] does not exists!", productRequest.id())));

        productEntity.setName(productRequest.name());
        productEntity.setQuantity(productRequest.quantity());
        productEntity.setLocationInStorage(productRequest.locationInStorage());
        productEntity.setDescription(productRequest.description());
        productEntity.setProducer(productRequest.producer());
        productEntity.setBestBeforeDate(productRequest.bestBeforeDate().plusDays(1));
        productEntity.setPrice(productRequest.price());
        productEntity.setUnit(productRequest.unit());
        productEntity.setSupplier(productRequest.supplier());
        ProductResponse responseForEditAction = productMapper.mapFromEntity(productJpaRepository.save(productEntity));

        log.info("Product with id {} has been edit successfully", productRequest.id());
        return responseForEditAction;
    }

}
