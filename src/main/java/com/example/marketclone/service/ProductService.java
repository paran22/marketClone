package com.example.marketclone.service;

import com.example.marketclone.model.Product;
import com.example.marketclone.repository.ProductRepository;
import com.example.marketclone.requestDto.ProductRequestDto;
import com.example.marketclone.responseDto.ProductResponseDto;
import com.example.marketclone.testData.SeleniumRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    //private final SeleniumRunner seleniumRunner;

    // 전체 상품 조회 메소드
    public List<ProductResponseDto> getAllProducts(int page, int size) {

        // 페이징
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Product> foundProductList = productRepository.findAll(pageable);

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

        // 상품 조회
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        return new ProductResponseDto(foundProduct);
    }

    //Test용 product 등록 메소드 - TestDataRunner에서 사용
    public void testRegisterProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto);
        productRepository.save(product);
    }


    // RDS에 상품 등록 메소드
//    public void rdsRegisterProducts() {
//        List<Product> products = seleniumRunner.getProducts();
//        productRepository.saveAll(products);
//    }


}
