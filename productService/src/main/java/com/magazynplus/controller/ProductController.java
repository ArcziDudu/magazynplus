package com.magazynplus.controller;

import com.magazynplus.dto.ProductRequest;
import com.magazynplus.dto.ProductResponse;
import com.magazynplus.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<ProductResponse> addNewProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.saveNewProduct(productRequest.withBestBeforeDate(productRequest.bestBeforeDate().plusDays(1))));
    }

    @DeleteMapping(value = "/delete/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/find/{searchKey}")
    public ResponseEntity<List<ProductResponse>> searchProductBySearchKey(@PathVariable String searchKey) {
        return ResponseEntity.ok(productService.findBySearchKey(searchKey));
    }

    @GetMapping(value = "/all/{userId}/{page}")
    public ResponseEntity<List<ProductResponse>> fetchAllProductsByUser(@PathVariable Integer userId,
                                                                        @PathVariable Integer page) {
        return ResponseEntity.ok(productService.findAllByUser(userId, page));
    }

    @GetMapping(value = "/details/{productId}")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductDetails(productId));
    }

}
