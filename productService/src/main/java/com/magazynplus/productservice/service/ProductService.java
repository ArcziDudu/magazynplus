package com.magazynplus.productservice.service;

import com.magazynplus.productservice.dto.ProductRequest;
import com.magazynplus.productservice.dto.ProductResponse;
import com.magazynplus.productservice.model.ProductEntity;
import com.magazynplus.productservice.repository.ProductJpaRepository;
import com.magazynplus.productservice.service.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse saveNewProduct(ProductRequest productRequest) {
        ProductEntity save = productJpaRepository.save(productMapper.mapFromRequest(productRequest));
        return productMapper.mapFromEntity(save);
    }
}
