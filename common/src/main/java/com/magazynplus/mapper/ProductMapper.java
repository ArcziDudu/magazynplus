package com.magazynplus.mapper;

import com.magazynplus.dto.ProductRequest;
import com.magazynplus.dto.ProductResponse;
import com.magazynplus.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    default ProductResponse mapFromEntity(ProductEntity entity){
        return ProductResponse.builder()
                .id(entity.getId())
                .availability(entity.getAvailability())
                .productNumber(entity.getProductNumber())
                .name(entity.getName())
                .category(entity.getCategory())
                .producer(entity.getProducer())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .unit(entity.getUnit())
                .quantity(entity.getQuantity())
                .locationInStorage(entity.getLocationInStorage())
                .supplier(entity.getSupplier())
                .bestBeforeDate(entity.getBestBeforeDate())
                .build();
    }
    default ProductEntity mapFromRequest(ProductRequest request){
        return ProductEntity.builder()
                .productNumber(String.valueOf(UUID.randomUUID()))
                .name(request.name())
                .price(request.price())
                .availability(true)
                .producer(request.producer())
                .category(request.category())
                .description(request.description())
                .quantity(request.quantity())
                .unit(request.unit())
                .supplier(request.supplier())
                .bestBeforeDate(request.bestBeforeDate())
                .locationInStorage(request.locationInStorage())
                .build();
    }
}
