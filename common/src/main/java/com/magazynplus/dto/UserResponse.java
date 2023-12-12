package com.magazynplus.dto;

import com.magazynplus.entity.ProductEntity;

import java.util.List;

public record UserResponse(Integer id,
                           String email,
                           String firstname,
                           String lastname,
                           List<ProductEntity> products) {
}
