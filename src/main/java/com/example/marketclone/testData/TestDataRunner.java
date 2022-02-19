package com.example.marketclone.testData;


import com.example.marketclone.model.Cart;
import com.example.marketclone.model.User;
import com.example.marketclone.repository.CartRepository;
import com.example.marketclone.repository.ProductInCartRepository;
import com.example.marketclone.repository.UserRepository;
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

        // 마켓컬리 크롤링해서 데이터 저장
        productService.registerProducts();
        System.out.println("크롤링 완료");

        // 테스트 User 생성
        Cart cart = new Cart(0L, 3000L);
        cartRepository.save(cart);
        User testUser1 = new User("abc", passwordEncoder.encode("aaaa1234!"),
                "abc@abc.abc", "르탄이", cart);

        userRepository.save(testUser1);


    }
}
