package com.magazynplus.service;


import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.entity.SupplierEntity;
import com.magazynplus.repository.SupplierRepository;
import com.magazynplus.repository.mapper.SupplierMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    @Transactional
    public SupplierEntity createSupplier(SupplierRequest request) {
        SupplierEntity supplierEntity = supplierMapper.mapFromRequest(request);
        System.out.println(supplierEntity);
        return supplierRepository.save(supplierEntity);
    }
}
