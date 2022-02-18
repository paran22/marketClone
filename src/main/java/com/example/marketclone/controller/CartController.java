package com.example.marketclone.controller;


import com.example.marketclone.dto.CartRequestDto;
import com.example.marketclone.dto.CartResponseDto;
import com.example.marketclone.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartService cartService;

    //장바구니 담기 POST
    @PostMapping("/cart/{productId}")
    public void saveCart(@PathVariable Long productId, @RequestBody CartRequestDto cartRequestDto) {
        cartService.saveCarts(productId, cartRequestDto);
    }

    //장바구니 조회 GET
    @GetMapping("/cart")
    public List<CartResponseDto> getAllCart() {
        return cartService.getAllCarts();
    }

    //장바구니 수량 변경하기 PUT
    @PutMapping("/cart/{productInCartId}")
    public void editCart(@PathVariable Long productInCartId, @RequestBody CartRequestDto cartRequestDto) {
        cartService.editCarts(productInCartId, cartRequestDto);
    }

    //장바구니 삭제 DELETE
    @DeleteMapping("/cart/{productInCartId}")
    public void deleteCart(@PathVariable Long productInCartId) {
        cartService.deleteCarts(productInCartId);
    }


}
