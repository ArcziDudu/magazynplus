package com.magazynplus.productservice.service.mapper;

import com.magazynplus.productservice.dto.ProductRequest;
import com.magazynplus.productservice.dto.ProductResponse;
import com.magazynplus.productservice.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "availability", source = "entity.availability")
    @Mapping(target = "productNumber", source = "entity.productNumber")
    ProductResponse mapFromEntity(ProductEntity entity);
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
                .imageLink(request.imageLink())
                .build();
    }
}
