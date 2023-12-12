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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductJpaRepository productJpaRepository;
    private final WebClient.Builder webClientBuilder;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    @Transactional
    public ProductResponse saveNewProduct(ProductRequest productRequest) {
        try {
            UserResponse user = webClientBuilder.build().get()
                    .uri("http://user-service/api/user/info/2")
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();


            ProductEntity productEntity = productMapper.mapFromRequest(productRequest);
            productEntity.setUser(userMapper.mapFromRequest(user));
            ProductEntity save = productJpaRepository.save((productEntity));
            return productMapper.mapFromEntity(save);
        } catch (RuntimeException e) {
            {
                throw new UserNotFoundException(String.format("User with id [%s] does not exists!", 2));
            }
        }
    }


    @Transactional
    public void deleteProductById(Long productId) {
        Optional<ProductEntity> productById = productJpaRepository.findById(productId);
        if (productById.isEmpty()) {
            throw new ProductNotFoundException(
                    String.format("Product with id [%s] does not exists!", productId));
        } else {
            productJpaRepository.deleteById(productId);
        }
    }
}
