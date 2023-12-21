package com.magazynplus.dto;

import com.magazynplus.entity.ProductEntity;
import com.magazynplus.entity.SupplierEntity;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record UserResponse(Integer id,
                           String email,
                           String firstname,
                           String lastname,
                           List<ProductEntity> products) {
}
