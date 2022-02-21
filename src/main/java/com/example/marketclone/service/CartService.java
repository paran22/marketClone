package com.example.marketclone.service;

import com.example.marketclone.requestDto.CartRequestDto;

import com.example.marketclone.requestDto.OrderRequestDto;
import com.example.marketclone.responseDto.CartResponseDto;


import com.example.marketclone.model.*;
import com.example.marketclone.repository.*;

import com.example.marketclone.responseDto.ProductResponseDto;
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
    public CartResponseDto saveCart(Long productId, CartRequestDto cartRequestDto, UserDetailsImpl userDetails) {
        Long count = cartRequestDto.getCount();
        //로그인한 유저 userdetail
        //Cart에서 ProductInCart를 즉시로딩으로 불러오니 해결됨
        Cart cart = userDetails.getUser().getCart();
        String state = "cart";

        //상품찾아서 ProductResponseDto에 담기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        ProductResponseDto productResponseDto = new ProductResponseDto(product);

        ProductInCart newProductInCart;

        List<ProductInCart> savedProductInCartList = cart.getProductInCartList();
        // 장바구니에 담긴 상품이 없으면 새로운 상품 저장
        if (savedProductInCartList.size() == 0) {
            //그 유저가 선택한 프로덕트를 저장
            newProductInCart = ProductInCart.addProductInCart(product, count, state, cart);   // 여기에 product, count, state, cartId
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
                        return new CartResponseDto(productInCart.getId(), productResponseDto, productInCart.getCount());
                    }
                }
            } // 새로운 상품은 저장
            //그 유저가 선택한 프로덕트를 저장
            newProductInCart = ProductInCart.addProductInCart(product, count, state, cart);   // 여기에 product, count, state, cartId
            productInCartRepository.save(newProductInCart);
        }
        return new CartResponseDto(newProductInCart.getId(), productResponseDto, count);
    }



//    private ProductInCart saveProductInCart(Long productId, Cart cart, Long count, String state) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
//        //그 유저가 선택한 프로덕트를 저장
//        ProductInCart newProductInCart = ProductInCart.addProductInCart(product, count, state, cart);   // 여기에 product, count, state, cartId
//        productInCartRepository.save(newProductInCart);
//        return newProductInCart;
//    }


    //    // 장바구니 조회
    @Transactional
    public List<CartResponseDto> getAllCarts(UserDetailsImpl userDetails) {
// cartResponseDto에 1. Long ProductInCartId, 2. List<Product> product, 3. Long count
// 위 3가지를 찾아서  cartResponseDto에 이 3가지가 이 3가지이다를 알려주고 넣은 뒤
// arraylist 선언해서 cartResponseDtoList를 만들어준뒤 그 안에 넣고 리턴해주면된다

       // 반환할 리스트
        List<CartResponseDto> cartResponseDtoList = new ArrayList<>();

//    카트가 null 이 아니면 조건 넣어야된다?
//        Long cartId = userDetails.getUser().getCart().getId();
//까지 해서 cartId를 찾아서 그걸 List<CartResponseDto>형태로 넣어서 보내면 되는데 api명세서 보고 저거 하나하나 다 넣어줘야한다, for문을 돌려서서

        // 로그인한 유저의 장바구니 찾기
        Cart cart = userDetails.getUser().getCart();

        // 장바구니 전체를 불러와 리스트에 저장한다.
        List<ProductInCart> productInCartList = cart.getProductInCartList();

        //for문을 돌면서 각 정보들 추가해
        for (ProductInCart eachProductInCart : productInCartList) {
            // 1. Long ProductInCartId 찾았고
            Long productInCartId = eachProductInCart.getId();

            // productId를 이용해서 2. Product product 찾자
            Long productId = eachProductInCart.getProduct().getId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
            ProductResponseDto productResponseDto = new ProductResponseDto(product);

            // 3. Long count 찾았다
            Long count = eachProductInCart.getCount();

            // CartResponseDto에 1. Long ProductInCartId, 2. List<Product> product, 3. Long count 넣어준다
            CartResponseDto cartResponseDto = new CartResponseDto(productInCartId, productResponseDto, count);

            // 반환할 리스트에 하나씩 넣어준다
            cartResponseDtoList.add(cartResponseDto);
        }
        //리스트를 돌려준다.
        return cartResponseDtoList;
    }

    // 장바구니 수량 변경하기
    @Transactional
    public void editCarts(Long productInCartId, CartRequestDto cartRequestDto) {
        Long count = cartRequestDto.getCount();

        ProductInCart productInCart = productInCartRepository.findById(productInCartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니의 상품을 찾을 수 없습니다."));

        productInCart.setCount(count);
        productInCartRepository.save(productInCart);
    }


    // 장바구니 삭제   // 구현 테스트 완료
    @Transactional
    public void deleteCart(Long productInCartId) {
        ProductInCart productInCart = productInCartRepository.findById(productInCartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니의 상품을 찾을 수 없습니다."));
        // cart와 productInCart의 관계를 끊기
        productInCart.removeCart();
        productInCartRepository.delete(productInCart);
//        productInCartRepository.deleteById(productInCartId);

    }

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

        // user찾기
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("user를 찾을 수 없습니다."));

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




