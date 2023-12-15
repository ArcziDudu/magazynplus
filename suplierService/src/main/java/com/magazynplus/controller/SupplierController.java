package com.magazynplus.controller;


import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.entity.SupplierEntity;
import com.magazynplus.service.SupplierService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping(value = "/create")
    public ResponseEntity<SupplierEntity> createNewSupplier(@RequestBody SupplierRequest request){
        return ResponseEntity.ok(supplierService.createSupplier(request));
    }
}
