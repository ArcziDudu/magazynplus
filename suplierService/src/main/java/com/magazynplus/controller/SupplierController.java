package com.magazynplus.controller;


import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.dto.SupplierResponse;
import com.magazynplus.entity.SupplierEntity;
import com.magazynplus.service.SupplierService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping(value = "/create")
    public ResponseEntity<SupplierResponse> createNewSupplier(@RequestBody SupplierRequest supplierRequest, HttpServletRequest httpServletRequest) {
        System.out.println(supplierRequest);
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtTokenString = authorizationHeader.substring(7);
            return ResponseEntity.ok(supplierService.createSupplier(supplierRequest, jwtTokenString));
        } else {
            throw new RuntimeException("No Authorization header found");
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<SupplierResponse>>  fetchAllSuppliers(){
        return ResponseEntity.ok(supplierService.findAllByUser());
    }
    @GetMapping(value = "/details/{supplierId}")
    public ResponseEntity<SupplierResponse> getSupplierDetails(@PathVariable Integer supplierId){
        return ResponseEntity.ok(supplierService.getDetailsById(supplierId));
    }
    @DeleteMapping(value = "/delete/{supplierId}")
    public ResponseEntity<Void> deleteSupplierById(@PathVariable Integer supplierId){
        supplierService.deleteById(supplierId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/edit")
    public ResponseEntity<SupplierResponse> editSupplier(@RequestBody SupplierRequest request){
        return ResponseEntity.ok(supplierService.editSupplier(request));
    }
}
