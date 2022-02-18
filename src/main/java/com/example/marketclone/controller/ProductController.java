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

    // 상품 등록 API (프론트와 연결X)
    @PostMapping("/register")
    public Product registerProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.registerProduct(requestDto);
    }

    // 전체 상품 조회하기 API
    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품 상세 조회하기 API
    @GetMapping("/product/{id}")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}
