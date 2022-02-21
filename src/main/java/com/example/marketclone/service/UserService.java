package com.example.marketclone.service;

import com.example.marketclone.model.Cart;
import com.example.marketclone.model.User;
import com.example.marketclone.repository.CartRepository;
import com.example.marketclone.repository.UserRepository;
import com.example.marketclone.requestDto.SignupRequestDto;
import com.example.marketclone.responseDto.UserInfoDto;
import com.example.marketclone.security.UserDetailsImpl;
import com.example.marketclone.validation.SignupValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void registerUser(SignupRequestDto requestDto) {
        if (SignupValidation.validationSignupInput(requestDto)) {
            String username = requestDto.getUsername();
            if (userRepository.existsByUsername(username)) {
                throw new IllegalArgumentException("중복된 username 입니다.");
            }
            // 패스워드 암호화
            String password = passwordEncoder.encode(requestDto.getPassword());
            String email = requestDto.getEmail();
            String name = requestDto.getName();

            // 장바구니 생성하기
            Cart cart = new Cart();
            cartRepository.save(cart);

            User user = new User(username, email, password, name, cart);
            userRepository.save(user);
        }
    }

    public UserInfoDto getUserInfo(UserDetailsImpl userDetails) {
        return new UserInfoDto(
                userDetails.getUser().getUsername(),
                userDetails.getUser().getName()
        );
    }
}
