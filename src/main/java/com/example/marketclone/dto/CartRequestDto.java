package com.example.marketclone.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {

    private Long count;

    public CartRequestDto(Long count) {
        this.count = count;
    }
}
