package com.example.marketclone.testData;

import com.example.marketclone.model.Product;
import com.example.marketclone.repository.ProductRepository;
import com.example.marketclone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDataRunner implements ApplicationRunner {

    @Autowired
    ProductService productService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 마켓컬리 크롤링해서 데이터 저장
        productService.registerProducts();
        System.out.println("크롤링 완료");

    }
}
