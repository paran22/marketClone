package com.example.marketclone.service;

import com.example.marketclone.dto.CartRequestDto;
import com.example.marketclone.model.*;
import com.example.marketclone.repository.*;
import com.example.marketclone.requestDto.OrderRequestDto;
import com.example.marketclone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductInCartRepository productInCartRepository;
    private final OrderRepository orderRepository;


    // 장바구니 담기
    @Transactional
    public void saveCart(Long productId, CartRequestDto cartRequestDto, UserDetailsImpl userDetails ) {
        Long count = cartRequestDto.getCount();
        //로그인한 유저 userdetail
        //Cart에서 ProductInCart를 즉시로딩으로 불러오니 해결됨
        Cart cart = userDetails.getUser().getCart();
//        Long cartId = userDetails.getUser().getCart().getId();
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new IllegalArgumentException("카트없다!!!!"));
        String state = "cart";

        List<ProductInCart> savedProductInCartList = cart.getProductInCartList();
        // 장바구니에 담긴 상품이 없으면 새로운 상품 저장
        if (savedProductInCartList.size() == 0) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
            //그 유저가 선택한 프로덕트를 저장
            ProductInCart newProductInCart = ProductInCart.addProductInCart(product, count, state, cart);   // 여기에 product, count, state, cartId
            productInCartRepository.save(newProductInCart);
        // 장바구니에 담긴 상품이 있으면
        } else {
            // 장바구니에 담긴 상품id List를 만들기
            List<Long> savedProductIdList = new ArrayList<>();
            for (ProductInCart productInCart : savedProductInCartList) {
                savedProductIdList.add(productInCart.getProduct().getId());
            }
            // 이미 담긴 상품이면 count+
            if (savedProductIdList.contains(productId)) {
                for (ProductInCart productInCart : savedProductInCartList) {
                    if (productInCart.getProduct().getId().equals(productId)) {
                        productInCart.setCount(productInCart.getCount() + count);
                        productInCartRepository.save(productInCart);
                    }
                }
            // 새로운 상품은 저장
            } else {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
                //그 유저가 선택한 프로덕트를 저장
                ProductInCart newProductInCart = ProductInCart.addProductInCart(product, count, state, cart);   // 여기에 product, count, state, cartId
                productInCartRepository.save(newProductInCart);
            }
        }
    }
//
    // 장바구니 조회
//    public List<CartResponseDto> getAllCarts(UserDetailsImpl userDetails) {
//        Cart cart = userDetails.getUser().getCart();
//
//    }

    // 장바구니 조회하기
    public Cart getCart(UserDetailsImpl userDetails) {
        //Cart에서 ProductInCart를 즉시로딩으로 불러오니 해결됨
        Cart cart = userDetails.getUser().getCart();
//        Long cartId = userDetails.getUser().getCart().getId();
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(()-> new IllegalArgumentException("카트없다!!!!"));
        return cart;
    }


//    // 장바구니 수량 변경하기
//    public void editCarts(Long productInCartId, CartRequestDto cartRequestDto) {
//    }


//    // 장바구니 삭제
//    public void deleteCart(Long productInCartId) {
//        productInCartRepository.deleteAllByProductInCartId(productInCartId);
//        cartRepository.deleteAllByProductInCartId(productInCartId);
//    }


    // 주문하기
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
//        Cart cart = user.getCart();

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
    }
}




