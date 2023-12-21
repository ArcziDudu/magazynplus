package com.magazynplus.repository.mapperImpl;

import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.dto.SupplierResponse;
import com.magazynplus.entity.SupplierEntity;
import com.magazynplus.repository.mapper.SupplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SupplierMapperImpl implements SupplierMapper{

    @Override
    public SupplierEntity mapFromRequest(SupplierRequest request) {
        return SupplierEntity.builder()
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .address(request.address())
                .postalCode(request.postalCode())
                .nip(request.nip())
                .build();
    }

    @Override
    public SupplierResponse mapFromEntity(SupplierEntity save) {
        return SupplierResponse.builder()
                .id(save.getId())
                .name(save.getName())
                .phoneNumber(save.getPhoneNumber())
                .postalCode(save.getPostalCode())
                .address(save.getAddress())
                .email(save.getEmail())
                .nip(save.getNip())
                .build();
    }
}
