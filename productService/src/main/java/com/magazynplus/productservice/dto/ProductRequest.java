package com.magazynplus.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
public record ProductRequest(String name,
                             String category,
                             String producer,
                             BigDecimal price,
                             Integer quantity,
                             String description,
                             String imageLink) {

}
