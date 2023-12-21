package com.magazynplus.dto;

import com.magazynplus.entity.UserEntity;
import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@With
public record ProductResponse(Long id,
                              String name,
                              Boolean availability,
                              String productNumber,
                              String category,
                              String producer,
                              String supplier,
                              BigDecimal price,
                              Double quantity,
                              String description,
                              String locationInStorage,
                              String unit,
                              LocalDate bestBeforeDate) {
}
