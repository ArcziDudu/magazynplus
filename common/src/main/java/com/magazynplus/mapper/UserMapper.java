package com.magazynplus.mapper;

import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse mapFromEntity(UserEntity entity);
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
