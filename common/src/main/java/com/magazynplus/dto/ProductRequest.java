package com.magazynplus.dto;

import com.magazynplus.entity.UserEntity;
import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;

@Builder
@With
public record ProductRequest(String name,
                             String category,
                             String producer,
                             BigDecimal price,
                             Integer quantity,
                             String description,
                             String imageLink) {

}
