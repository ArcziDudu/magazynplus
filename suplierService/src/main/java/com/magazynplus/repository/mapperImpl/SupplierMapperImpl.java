package com.magazynplus.repository.mapperImpl;

import com.magazynplus.dto.SupplierRequest;
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
                .Name(request.Name())
                .phone(request.phone())
                .address(request.address())
                .nip(request.nip())
                .build();
    }
}
