package com.example.marketclone.controller;

import com.example.marketclone.model.Product;
import com.example.marketclone.requestDto.ProductRequestDto;
import com.example.marketclone.responseDto.ProductResponseDto;
import com.example.marketclone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    // 전체 상품 조회하기 API
    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품 상세 조회하기 API
    @GetMapping("/product/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }
}
