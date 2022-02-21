package com.example.marketclone.service;

import com.example.marketclone.model.Order;
import com.example.marketclone.model.ProductInCart;
import com.example.marketclone.repository.OrderRepository;
import com.example.marketclone.responseDto.OrderResponseDto;
import com.example.marketclone.responseDto.ProductResponseDto;
import com.example.marketclone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public List<OrderResponseDto> getOrders(UserDetailsImpl userDetails) {
        //LazyInitializationException 발생 -> repository로 조회시 해결
        List<Order> orderList = orderRepository.findAllByUser(userDetails.getUser());
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for(Order order : orderList) {
            List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
            for(ProductInCart productInCart : order.getProductInCartList()) {
                    ProductResponseDto productResponseDto = new ProductResponseDto(
                            productInCart.getProduct());
                    productResponseDtoList.add(productResponseDto);
            }
            OrderResponseDto orderResponseDto = new OrderResponseDto(
                    order.getId(),
                    order.getCreatedAt(),
                    productResponseDtoList,
                    order.getTotalPrice(),
                    order.getDeliveryFee(),
                    order.getState());
            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }
}
