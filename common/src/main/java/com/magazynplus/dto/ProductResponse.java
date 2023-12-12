package com.magazynplus.dto;

import com.magazynplus.entity.UserEntity;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String name,
                              Boolean availability,
                              String productNumber,
                              String category,
                              String producer,
                              BigDecimal price,
                              Integer quantity,
                              String description,
                              String imageLink) {
}
