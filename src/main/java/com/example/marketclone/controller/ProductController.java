package com.example.marketclone.controller;

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
    public List<ProductResponseDto> getAllProducts(@RequestParam int page,
                                                   @RequestParam int size) {
        return productService.getAllProducts(page, size);
    }

    // 상품 상세 조회하기 API
    @GetMapping("/product/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    // 일회용 - 마켓컬리 크롤링해서 RDS에 데이터 저장
//    @GetMapping("/rdsregisterproducts")
//    public void registerProducts( ) {
//        productService.rdsRegisterProducts();
//        System.out.println("크롤링 완료");
//    }

}
