package com.example.marketclone.service;

import com.example.marketclone.model.*;
import com.example.marketclone.repository.OrderRepository;
import com.example.marketclone.repository.ProductInCartRepository;
import com.example.marketclone.repository.UserRepository;
import com.example.marketclone.requestDto.OrderRequestDto;
import com.example.marketclone.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductInCartRepository productInCartRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CartService(UserRepository userRepository, ProductInCartRepository productInCartRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productInCartRepository = productInCartRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void getOrder(OrderRequestDto orderRequestDto, UserDetailsImpl userDetails) {
        //productInCart 찾기
        List<Long> productInCartIdList = orderRequestDto.getProductInCartIdList();
        List<ProductInCart> productInCartList = new ArrayList<>();
        for (Long productInCartId :productInCartIdList ) {
            ProductInCart productInCart = productInCartRepository.findById(productInCartId)
                    .orElseThrow(() -> new IllegalArgumentException("productInCart를 찾을 수 없습니다."));
            productInCartList.add(productInCart);
        }

        // user와 cart 찾기
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("user를 찾을 수 없습니다."));
        Cart cart = user.getCart();

        Long sumPrice = 0L;
        Long totalPrice = 0L;
        Long deliveryFee = 3000L;

        // 새로운 order 생성
        for (ProductInCart productInCart : productInCartList ) {
            sumPrice += productInCart.getCount() * productInCart.getProduct().getPrice();
        }
        if(sumPrice >= 50000) {
            deliveryFee = 0L;
        }
        totalPrice = sumPrice + deliveryFee;

        Order order = Order.addOrder(totalPrice, deliveryFee, user);
        orderRepository.save(order);

        // productInCart 상태 변경, order 추가, cart 연결제거
        for (ProductInCart productInCart : productInCartList ) {
            productInCart.setState("order");
            productInCart.setOrder(order);
            productInCart.removeCart();
            productInCartRepository.save(productInCart);
        }

        // cart 업데이트


    }
}
