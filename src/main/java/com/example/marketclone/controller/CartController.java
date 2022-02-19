package com.example.marketclone.controller;



import com.example.marketclone.dto.CartRequestDto;
import com.example.marketclone.dto.CartResponseDto;
import com.example.marketclone.requestDto.OrderRequestDto;
import com.example.marketclone.security.UserDetailsImpl;
import com.example.marketclone.service.CartService;
import com.example.marketclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    //장바구니 담기 POST
    @PostMapping("/cart/{productId}")
    public void saveCart(@PathVariable Long productId, @RequestBody CartRequestDto cartRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인 사용자만 이용할 수 있습니다.");
        }
//
        cartService.saveCart(productId, cartRequestDto, userDetails);
    }
//
//    //장바구니 조회 GET
//    @GetMapping("/cart")
//    public List<CartResponseDto> getAllCarts() {
//        return cartService.getAllCarts();
//    }

    //장바구니 수량 변경하기 PUT
    @PutMapping("/cart/{productInCartId}")
    public void editCarts(@PathVariable Long productInCartId, @RequestBody CartRequestDto cartRequestDto) {
        cartService.editCarts(productInCartId, cartRequestDto);
    }

    //장바구니 삭제 DELETE
    @DeleteMapping("/cart/{productInCartId}")
    public void deleteCart(@PathVariable Long productInCartId) {
        cartService.deleteCart(productInCartId);
    }

    // 주문하기
    @PostMapping("/order")
    public void getOrder(@RequestBody OrderRequestDto orderRequestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.getOrder(orderRequestDto, userDetails);
    }


}
