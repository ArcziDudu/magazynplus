package com.magazynplus.mapper;

import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    default UserResponse mapFromEntity(UserEntity entity){
        return UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .products(entity.getProducts())
                .build();
    }
    default UserEntity mapFromRequest(UserResponse response){
        return UserEntity.builder()
                .id(response.id())
                .email(response.email())
                .firstname(response.firstname())
                .lastname(response.lastname())
                .products(response.products())
                .build();
    }
}
