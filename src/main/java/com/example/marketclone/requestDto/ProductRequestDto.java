package com.example.marketclone.requestDto;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String productName;
    private int price;
    private String desc;
    private String productImg;
}
