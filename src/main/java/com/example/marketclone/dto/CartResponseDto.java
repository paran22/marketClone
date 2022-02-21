package com.example.marketclone.dto;


import com.example.marketclone.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {

    private Long productInCartId;
    private Product product;
    private Long count;

    public CartResponseDto(Long productInCartId, Product product, Long count) {
        this.productInCartId = productInCartId;
        this.product = product;
        this.count = count;
    }

}
