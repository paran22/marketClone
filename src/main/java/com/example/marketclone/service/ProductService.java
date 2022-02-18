package com.example.marketclone.service;

import com.example.marketclone.model.Product;
import com.example.marketclone.repository.ProductRepository;
import com.example.marketclone.requestDto.ProductRequestDto;
import com.example.marketclone.responseDto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록 메소드
    public Product registerProduct(ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }

    // 전체 상품 조회 메소드
    public List<ProductResponseDto> getAllProducts() {
        List<Product> foundProductList = productRepository.findAll();

        // 찾아온 Product들을 PrductResponseDto에 담는 과정
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for(Product product : foundProductList) {
            ProductResponseDto responseDto = new ProductResponseDto(product);
            productResponseDtoList.add(responseDto);
        }

        return productResponseDtoList;
    }

    // 상품 상세 조회 메소드
    public ProductResponseDto getProduct(Long id) {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));
        return new ProductResponseDto(foundProduct);
    }


}
