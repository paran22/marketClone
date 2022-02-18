package com.example.marketclone.service;

import com.example.marketclone.dto.CartRequestDto;
import com.example.marketclone.dto.CartResponseDto;
import com.example.marketclone.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;



    // 장바구니 담기
    public void saveCarts(Long productId, CartRequestDto cartRequestDto) {
    }
//
//    // 장바구니 조회
//    public List<CartResponseDto> getAllCarts() {
//    }


    // 장바구니 수량 변경하기
    public void editCarts(Long productInCartId, CartRequestDto cartRequestDto) {
    }


    // 장바구니 삭제
    public void deleteCarts(Long productInCartId) {
    }




}
