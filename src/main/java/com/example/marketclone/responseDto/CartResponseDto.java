package com.example.marketclone.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {

    private Long productInCartId;
    private ProductResponseDto product;
    private Long count;

    public CartResponseDto(Long productInCartId, ProductResponseDto productResponseDto, Long count) {
        this.productInCartId = productInCartId;
        this.product = productResponseDto;
        this.count = count;
    }

}
