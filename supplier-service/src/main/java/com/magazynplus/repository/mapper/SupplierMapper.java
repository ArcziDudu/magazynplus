package com.magazynplus.repository.mapper;

import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.dto.SupplierResponse;
import com.magazynplus.entity.SupplierEntity;
import org.springframework.stereotype.Component;

@Component
public interface SupplierMapper {
    SupplierEntity mapFromRequest(SupplierRequest request);

    SupplierResponse mapFromEntity(SupplierEntity save);
}
