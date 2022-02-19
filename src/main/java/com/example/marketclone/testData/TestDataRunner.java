package com.example.marketclone.testData;

import com.example.marketclone.model.Cart;
import com.example.marketclone.model.Product;
import com.example.marketclone.model.ProductInCart;
import com.example.marketclone.model.User;
import com.example.marketclone.repository.CartRepository;
import com.example.marketclone.repository.ProductInCartRepository;
import com.example.marketclone.repository.UserRepository;
import com.example.marketclone.requestDto.ProductRequestDto;
import com.example.marketclone.requestDto.SignupRequestDto;
import com.example.marketclone.service.ProductService;
import com.example.marketclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataRunner implements ApplicationRunner {

    @Autowired
    ProductService productService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductInCartRepository productInCartRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 테스트 User 생성
        Cart cart = new Cart(0L, 3000L);
        cartRepository.save(cart);
        User testUser1 = new User("abc", passwordEncoder.encode("aaaa1234!"),
                "abc@abc.abc", "르탄이", cart);

        userRepository.save(testUser1);

        // 저장할 상품들
        ProductRequestDto requestDto1 = new ProductRequestDto(
                "[풀무원] 올바른 핫도그 5개입",
                6300,
                "담백하게 즐기는 온 가족 간식",
                "https://img-cf.kurly.com/shop/data/goodsview/20211217/gv10000259282_1.jpg");

        ProductRequestDto requestDto2 = new ProductRequestDto(
                "1등급 한우 안심 스테이크 200g (냉장)",
                42000,
                "100g당 가격:21,000",
                "https://img-cf.kurly.com/shop/data/goodsview/20200311/gv40000085142_1.jpg");

        ProductRequestDto requestDto3 = new ProductRequestDto(
                "친환경 하우스 딸기 (설향) 500g",
                15400,
                "촉촉하게 번지는 새콤달콤함",
                "https://img-cf.kurly.com/shop/data/goodsview/20201229/gv40000140774_1.jpg");

        ProductRequestDto requestDto4 = new ProductRequestDto(
                "친환경 피망",
                3790,
                "어느요리에도 활용가능한 만능채소(1봉 2입)",
                "https://img-cf.kurly.com/shop/data/goodsview/20170703/gv00000004729_1.jpg");

        ProductRequestDto requestDto5 = new ProductRequestDto(
                "[글래드] 지퍼백/매직백 11종",
                3200,
                "신선한 보관을 위한 주방 필수품",
                "https://img-cf.kurly.com/shop/data/goodsview/20220216/gv30000280784_1.jpg");

        ProductRequestDto requestDto6 = new ProductRequestDto(
                "[데체코] 엑스트라버진 올리브 오일",
                9500,
                "이탈리아의 우수 품종 올리브를 냉압착한 엑스트라버진 올리브유",
                "https://img-cf.kurly.com/shop/data/goodsview/20170320/gv00000001034_1.jpg");

        ProductRequestDto requestDto7 = new ProductRequestDto(
                "친환경 감자 600g",
                2690,
                "어떤 음식을 해도 잘 어울리는 무농약 감자",
                "https://img-cf.kurly.com/shop/data/goodsview/20170807/gv40000005913_1.jpg");

        ProductRequestDto requestDto8 = new ProductRequestDto(
                "[상하목장] 유기농 요구르트 플레인 400g",
                3400,
                "건강한 홈메이드 스타일",
                "https://img-cf.kurly.com/shop/data/goods/daily/1bf6f2425023ce519987.jpg");

        ProductRequestDto requestDto9 = new ProductRequestDto(
                "[MAC] 파우더 키스 립스틱, 디보티드 투 칠리",
                33000,
                "깊고 그윽한 브릭 레드 컬러",
                "https://img-cf.kurly.com/shop/data/goodsview/20220216/gv10000280764_1.jpg");

        ProductRequestDto requestDto10 = new ProductRequestDto(
                "[Kurly's] 물티슈 2종",
                8200,
                "믿고 쓰는 우리 집 물티슈",
                "https://img-cf.kurly.com/shop/data/goodsview/20220215/gv20000280347_1.jpg");

        // 상품 저장
        Product product1, product2;
        product1 = productService.registerProduct(requestDto1);
        product2 = productService.registerProduct(requestDto2);
        productService.registerProduct(requestDto3);
        productService.registerProduct(requestDto4);
        productService.registerProduct(requestDto5);
        productService.registerProduct(requestDto6);
        productService.registerProduct(requestDto7);
        productService.registerProduct(requestDto8);
        productService.registerProduct(requestDto9);
        productService.registerProduct(requestDto10);

        // 테스트 Cart, ProductInCart 생성
        cart.setTotalPrice(102900L);
        cart.setDeliveryFee(0L);
        cartRepository.save(cart);
        testUser1.setCart(cart);
        userRepository.save(testUser1);
        Long count1 = 3L;
        Long count2 = 2L;
        String state = "cart";
        ProductInCart productInCart1 = new ProductInCart(product1, count1, state, testUser1.getCart());
        ProductInCart productInCart2 = new ProductInCart(product2, count2, state, testUser1.getCart());
        productInCartRepository.save(productInCart1);
        productInCartRepository.save(productInCart2);
    }
}
