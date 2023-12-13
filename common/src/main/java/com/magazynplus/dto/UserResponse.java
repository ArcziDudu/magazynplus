package com.magazynplus.dto;

import com.magazynplus.entity.ProductEntity;
import lombok.Builder;

import java.util.List;
@Builder
public record UserResponse(Integer id,
                           String email,
                           String firstname,
                           String lastname,
                           List<ProductEntity> products) {
}
