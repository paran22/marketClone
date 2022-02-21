package com.example.marketclone.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OrderResponseDto {

    private Long orderId;
    private String createdAt;
    private List<ProductResponseDto> product;
    private Long totalPrice;
    private Long deliveryFee;
    private String state;


//    private List<OrderResponseDto> orderResponseDtoList;
//
//    @AllArgsConstructor
//    @Setter
//    @Getter
//    public static class OrderResponseDto {
//        private Long orderId;
//        private String createdAt;
//        private List<ProductResponseDto> product;
//        private Long totalPrice;
//        private Long deliveryFee;
//        private String state;
//    }


}
