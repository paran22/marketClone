package com.example.marketclone.controller;

import com.example.marketclone.requestDto.OrderRequestDto;
import com.example.marketclone.security.UserDetailsImpl;
import com.example.marketclone.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    // 주문하기
    @PostMapping("/order")
    public void getOrder(@RequestBody OrderRequestDto orderRequestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.getOrder(orderRequestDto, userDetails);
    }

}
