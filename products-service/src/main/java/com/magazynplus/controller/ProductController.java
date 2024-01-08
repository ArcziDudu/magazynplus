package com.magazynplus.controller;


import com.magazynplus.dto.ProductRequest;
import com.magazynplus.dto.ProductResponse;
import com.magazynplus.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addNewProduct(@RequestBody ProductRequest productRequest, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtTokenString = authorizationHeader.substring(7);
            return ResponseEntity.ok(productService
                    .saveNewProduct
                            (productRequest.withBestBeforeDate(productRequest.bestBeforeDate().plusDays(1)), jwtTokenString)).getBody();
        }
        throw new RuntimeException("No Authorization header found");
    }


    @PatchMapping("/edit")
    public ResponseEntity<ProductResponse> editProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.editProduct(productRequest));
    }


    @DeleteMapping(value = "/delete/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/find/{searchKey}")
    public ResponseEntity<List<ProductResponse>> searchProductBySearchKey(@PathVariable String searchKey, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtTokenString = authorizationHeader.substring(7);
            return ResponseEntity.ok(productService.findBySearchKey(searchKey, jwtTokenString));
        }
        throw new RuntimeException("No Authorization header found");
    }

    @GetMapping(value = "/all/{page}")
    public ResponseEntity<List<ProductResponse>> fetchAllProductsByUser(@PathVariable Integer page, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtTokenString = authorizationHeader.substring(7);
            return ResponseEntity.ok(productService.findAllByUser(page, jwtTokenString));
        }
        throw new RuntimeException("No Authorization header found");
    }

    @GetMapping(value = "/details/{productId}")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductDetails(productId));
    }

}
