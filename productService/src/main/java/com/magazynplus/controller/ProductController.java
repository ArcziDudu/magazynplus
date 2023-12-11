package com.magazynplus.controller;

import com.magazynplus.dto.ProductRequest;
import com.magazynplus.dto.ProductResponse;
import com.magazynplus.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<ProductResponse> addNewProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.saveNewProduct(productRequest));
    }

    @DeleteMapping(value = "/delete/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

}
