package com.example.marketclone.service;


import com.example.marketclone.dto.CartRequestDto;
import com.example.marketclone.dto.CartResponseDto;
import com.example.marketclone.model.*;
import com.example.marketclone.repository.*;

import com.example.marketclone.requestDto.OrderRequestDto;
import com.example.marketclone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductInCartRepository productInCartRepository;
    private final OrderRepository orderRepository;


    // 장바구니 담기
    public void saveCart(Long productId, CartRequestDto cartRequestDto, UserDetailsImpl userDetails ) {
        Long count = cartRequestDto.getCount();

//        Cart cart = new Cart(productId,cartRequestDto);
//        cartRepository.save(cart);

        // 로그인한 유저 userdetail
        Long userId = userDetails.getUser().getId();
        Optional<User> user = userRepository.findById(userId);
        User user1 = user.get();

        pro
//        String string = productInCartRepository.toString(state);
//        ProductInCart productInCart = new ProductInCart();

//        //그 유저가 선택한 프로덕트를 저장
        Optional<Product> product1 = productRepository.findById(productId);
        Product product = product1.get();

        Optional<Cart> cart = cartRepository.findById(productId);
        Cart cart1 = cart.get();

        // productInCart를 만들어주는거야
        ProductInCart productInCart = ProductInCart.addProductInCart(product, count, state, cart1 );   // 여기에 product, count, state, cartId

        productInCartRepository.save(productInCart);

    }

//    // 장바구니 조회
//    public List<CartResponseDto> getAllCarts() {
//
//    }


    // 장바구니 수량 변경하기
    public void editCarts(Long productInCartId, CartRequestDto cartRequestDto) {
    }


    // 장바구니 삭제
    public void deleteCart(Long productInCartId) {
        productInCartRepository.deleteAllByProductInCartId(productInCartId);
        cartRepository.deleteAllByProductInCartId(productInCartId);
    }


    @Transactional
    public void getOrder(OrderRequestDto orderRequestDto, UserDetailsImpl userDetails) {
        List<Long> productInCartIdList = orderRequestDto.getProductInCartIdList();
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("user를 찾을 수 없습니다"));
        Cart cart = user.getCart();

        Long totalPrice;
        Long deliveryFee;

        // 새로운 order 생성
//        for (ProductInCart productInCart : cart.getProductInCartList() ) {
//            totalPrice += productInCart.getCount() * productInCart.getProduct().getPrice();
//        }

        Order order = Order.addOrder(cart.getTotalPrice(), cart.getDeliveryFee(), user);
        orderRepository.save(order);

        // productInCart 상태 변경, order 추가, cart 연결제거
        for (ProductInCart productInCart : cart.getProductInCartList()) {
            productInCart.setState("order");
            productInCart.setOrder(order);
            productInCart.removeCart();
            productInCartRepository.save(productInCart);
        }

        // cart 업데이트


    }
}




