package com.example.marketclone.responseDto;

import com.example.marketclone.model.OrderState;
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
    private OrderState state;

}
