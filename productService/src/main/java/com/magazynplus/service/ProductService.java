package com.magazynplus.service;


import com.magazynplus.dto.ProductRequest;
import com.magazynplus.dto.ProductResponse;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.exception.ProductNotFoundException;
import com.magazynplus.mapper.ProductMapper;
import com.magazynplus.repository.ProductJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductJpaRepository productJpaRepository;

    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse saveNewProduct(ProductRequest productRequest) {
        ProductEntity save = productJpaRepository.save(productMapper.mapFromRequest(productRequest));
        return productMapper.mapFromEntity(save);
    }


    @Transactional
    public void deleteProductById(Long productId) {
        Optional<ProductEntity> productById = productJpaRepository.findById(productId);
        if(productById.isEmpty()){
            throw new ProductNotFoundException(
                    String.format("Product with id [%s] does not exists!", productId));
        }else {
            productJpaRepository.deleteById(productId);
        }
    }
}
