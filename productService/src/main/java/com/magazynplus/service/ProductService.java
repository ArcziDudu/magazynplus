package com.magazynplus.service;


import com.magazynplus.dto.ProductRequest;
import com.magazynplus.dto.ProductResponse;
import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.exception.ProductNotFoundException;
import com.magazynplus.exception.UserNotFoundException;
import com.magazynplus.mapper.ProductMapper;
import com.magazynplus.mapper.UserMapper;
import com.magazynplus.repository.ProductJpaRepository;
import com.magazynplus.webClientService.UserInfoFetcherImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    @Transactional
    public ProductResponse saveNewProduct(ProductRequest productRequest, String jwtToken) {

        if (existsByBestBeforeDateAndNameAndUserId(productRequest)) {
            log.info("Found product by name {} and best before date {}", productRequest.name(), productRequest.bestBeforeDate());
            ProductEntity product = productJpaRepository.findByNameAndBestBeforeDate(productRequest.name(), productRequest.bestBeforeDate());
            product.setQuantity(product.getQuantity() + productRequest.quantity());
            log.info("Product quantity updated successfully");
            return productMapper.mapFromEntity(productJpaRepository.save(product));
        }
        try {
            UserResponse userResponse = userInfoFetcher.fetchUserInfo(jwtToken);
            ProductEntity productEntity = productMapper.mapFromRequest(productRequest);
            productEntity.setUser(userMapper.mapFromRequest(userResponse));
            ProductEntity save = productJpaRepository.save((productEntity));

            log.info("New product created successfully");
            return productMapper.mapFromEntity(save);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(String.format("User with id [%s] does not exists!", 2));
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }


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

    public List<ProductResponse> findBySearchKey(String searchKey) {
        return productJpaRepository.findByNameContainingAndUserId(searchKey, 2)
                .stream()
                .map(productMapper::mapFromEntity).toList();
    }

    public List<ProductResponse> findAllByUser(Integer userId, Integer page) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        return productJpaRepository.findByUserId(userId, pageable)
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
        productEntity.setBestBeforeDate(productRequest.bestBeforeDate());
        productEntity.setPrice(productRequest.price());
        productEntity.setUnit(productRequest.unit());
        productEntity.setSupplier(productRequest.supplier());
        ProductResponse responseForEditAction = productMapper.mapFromEntity(productJpaRepository.save(productEntity));

        log.info("Product with id {} has been edit successfully", productRequest.id());
        return responseForEditAction;
    }
}
