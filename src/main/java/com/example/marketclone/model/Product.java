package com.example.marketclone.model;

import com.example.marketclone.requestDto.ProductRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String desc;

    @Column(nullable = false)
    private String productImg;

    public Product(ProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.desc = requestDto.getDesc();
        this.productImg = requestDto.getProductImg();
    }

}
