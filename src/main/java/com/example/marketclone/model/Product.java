package com.example.marketclone.model;

import com.example.marketclone.requestDto.ProductRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String img;

    // fetch 기본값 LAZY
    @OneToMany(mappedBy = "product")
    private List<Comment> commentList = new ArrayList<>();

    public Product(ProductRequestDto requestDto) {
        this.name = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDesc();
        this.img = requestDto.getProductImg();
    }



}
