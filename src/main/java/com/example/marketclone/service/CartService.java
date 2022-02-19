package com.example.marketclone.service;

import com.example.marketclone.dto.CartRequestDto;
import com.example.marketclone.dto.CartResponseDto;
import com.example.marketclone.model.Cart;
import com.example.marketclone.model.Product;
import com.example.marketclone.model.ProductInCart;
import com.example.marketclone.model.User;
import com.example.marketclone.repository.CartRepository;

import com.example.marketclone.repository.ProductInCartRepository;
import com.example.marketclone.repository.ProductRepository;
import com.example.marketclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductInCartRepository productInCartRepository;




    // 장바구니 담기
    public void saveCart(Long productId, CartRequestDto cartRequestDto) {
//        Long count = cartRequestDto.getCount();

//        Cart cart = new Cart(productId,cartRequestDto);
//        cartRepository.save(cart);

        // 로그인한 유저

//        //그 유저가 선택한 프로덕트를 저장
//        Product product = new Product(productId);
//
//        productInCartRepository.save(product);

//
//        // procuctId로 product 객체 가져오기
//        Optional<Product> productTemp = productRepository.findById(productId);
//        Product product = productTemp.get();

//        Optional<ProductInCart> productInCart = productInCartRepository.findById(productId);
//        ProductInCart productInCart1 = productInCart.get();
//
//        Optional<User> user = userRepository.findById(productInCart1).get());
//        User user1 = user.get();
//
//        CartRepository cartRepository;
//        cartRepository.save(user1, productInCart1);


    }

    // 장바구니 조회
    public List<CartResponseDto> getAllCarts() {
      return
    }


    // 장바구니 수량 변경하기
    public void editCarts(Long productInCartId, CartRequestDto cartRequestDto) {
    }


    // 장바구니 삭제
    public void deleteCart(Long productInCartId) {
    }




}
