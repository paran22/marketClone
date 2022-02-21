package com.example.marketclone.testData;


import com.example.marketclone.model.Cart;
import com.example.marketclone.model.User;
import com.example.marketclone.repository.CartRepository;
import com.example.marketclone.repository.ProductInCartRepository;
import com.example.marketclone.repository.UserRepository;
import com.example.marketclone.requestDto.ProductRequestDto;
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

        // 저장할 상품들
        ProductRequestDto requestDto1 = new ProductRequestDto(
                "[풀무원] 올바른 핫도그 5개입",
                6300,
                "담백하게 즐기는 온 가족 간식",
                "https://img-cf.kurly.com/shop/data/goods/1639962988668y0.jpg");

        ProductRequestDto requestDto2 = new ProductRequestDto(
                "1등급 한우 안심 스테이크 200g (냉장)",
                42000,
                "100g당 가격:21,000",
                "https://img-cf.kurly.com/shop/data/goods/158390543787y0.jpg");

        ProductRequestDto requestDto3 = new ProductRequestDto(
                "친환경 하우스 딸기 (설향) 500g",
                15400,
                "촉촉하게 번지는 새콤달콤함",
                "https://img-cf.kurly.com/shop/data/goods/1609229005971y0.jpg");

        ProductRequestDto requestDto4 = new ProductRequestDto(
                "친환경 피망",
                3790,
                "어느요리에도 활용가능한 만능채소(1봉 2입)",
                "https://img-cf.kurly.com/shop/data/goods/1499060053485y0.jpg");

        ProductRequestDto requestDto5 = new ProductRequestDto(
                "[글래드] 지퍼백 4종",
                3200,
                "환경호르몬 걱정 없이 안전하고 신선하게",
                "https://img-cf.kurly.com/shop/data/goods/1566528090177y0.jpg");

        ProductRequestDto requestDto6 = new ProductRequestDto(
                "[데체코] 엑스트라버진 올리브 오일",
                9500,
                "이탈리아의 우수 품종 올리브를 냉압착한 엑스트라버진 올리브유",
                "https://img-cf.kurly.com/shop/data/goods/1452062613881y0.jpg");

        ProductRequestDto requestDto7 = new ProductRequestDto(
                "친환경 감자 600g",
                2690,
                "어떤 음식을 해도 잘 어울리는 무농약 감자",
                "https://img-cf.kurly.com/shop/data/goods/1456402788969y0.jpg");

        ProductRequestDto requestDto8 = new ProductRequestDto(
                "[상하목장] 유기농 요구르트 플레인 400g",
                3400,
                "건강한 홈메이드 스타일",
                "https://img-cf.kurly.com/shop/data/goods/1477288322952y0.jpg");

        ProductRequestDto requestDto9 = new ProductRequestDto(
                "[발뮤다] 더 팟 2종 K02B-WH",
                151000,
                "핸드 드립 커피, 감동의 경험이 되다.",
                "https://img-cf.kurly.com/shop/data/goods/1637925410952y0.jpeg");

        ProductRequestDto requestDto10 = new ProductRequestDto(
                "[Kurly's] 시그니처 물티슈 2종",
                1850,
                "컬리가 만든 물티슈의 최선",
                "https://img-cf.kurly.com/shop/data/goods/161053203517y0.jpg");

        // 상품 저장
        productService.testRegisterProduct(requestDto1);
        productService.testRegisterProduct(requestDto2);
        productService.testRegisterProduct(requestDto3);
        productService.testRegisterProduct(requestDto4);
        productService.testRegisterProduct(requestDto5);
        productService.testRegisterProduct(requestDto6);
        productService.testRegisterProduct(requestDto7);
        productService.testRegisterProduct(requestDto8);
        productService.testRegisterProduct(requestDto9);
        productService.testRegisterProduct(requestDto10);

        // 테스트 User 생성
        Cart cart = new Cart();
        cartRepository.save(cart);
        User testUser1 = new User("abc", passwordEncoder.encode("aaaa1234!"),
                "abc@abc.abc", "르탄이", cart);

        userRepository.save(testUser1);

    }
}
