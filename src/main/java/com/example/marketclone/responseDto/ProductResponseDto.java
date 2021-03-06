package com.example.marketclone.responseDto;

import com.example.marketclone.model.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private Long productId;
    private String productName;
    private int price;
    private String desc;
    private String productImg;

    public ProductResponseDto(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.price = product.getPrice();
        this.desc = product.getDescription();
        this.productImg = product.getImg();
    }
}
