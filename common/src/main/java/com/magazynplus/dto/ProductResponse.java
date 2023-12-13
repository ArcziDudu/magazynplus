package com.magazynplus.dto;

import com.magazynplus.entity.UserEntity;
import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
@Builder
@With
public record ProductResponse(Long id,
                              String name,
                              Boolean availability,
                              String productNumber,
                              String category,
                              String producer,
                              BigDecimal price,
                              Integer quantity,
                              String description,
                              String imageLink,
                              UserResponse userResponse) {
}
