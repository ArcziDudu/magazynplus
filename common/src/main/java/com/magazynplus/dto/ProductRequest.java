package com.magazynplus.dto;

import com.magazynplus.entity.UserEntity;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(String name,
                             String category,
                             String producer,
                             BigDecimal price,
                             Integer quantity,
                             String description,
                             String imageLink,
                             UserEntity user) {

}
