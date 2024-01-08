package com.magazynplus.dto;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@With
public record ProductRequest(Long id,
                             String name,
                             String category,
                             String producer,
                             BigDecimal price,
                             Double quantity,
                             String description,
                             Integer amount,
                             String supplier,
                             String unit,
                             String locationInStorage,
                             LocalDate bestBeforeDate
) {


}
