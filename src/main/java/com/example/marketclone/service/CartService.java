package com.example.marketclone.service;

import com.example.marketclone.model.Cart;
import com.example.marketclone.model.Order;
import com.example.marketclone.model.ProductInCart;
import com.example.marketclone.model.User;
import com.example.marketclone.repository.OrderRepository;
import com.example.marketclone.repository.ProductInCartRepository;
import com.example.marketclone.repository.UserRepository;
import com.example.marketclone.requestDto.OrderRequestDto;
import com.example.marketclone.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        for (ProductInCart productInCart : cart.getProductInCartList() ) {
            productInCart.setState("order");
            productInCart.setOrder(order);
            productInCart.removeCart();
            productInCartRepository.save(productInCart);
        }

        // cart 업데이트


    }
}
