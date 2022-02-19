package com.example.marketclone.validation;


import com.example.marketclone.requestDto.SignupRequestDto;

public class SignupValidation {
    public static boolean validationSignupInput(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        //username 확인
        //영어 대소문자, 숫자로 3~15자
        if (!username.matches("^[a-zA-Z0-9]{3,15}$")) {
            throw new IllegalArgumentException("username 조건이 맞지 않습니다.");
        }

        //email 확인
        if(!email.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")) {
            throw new IllegalArgumentException("email 형식에 맞지 않습니다.");
        }

        //password 확인
        //숫자, 영어 소문자, 특수문자 포함 8~15자 이내
        if(!password.matches("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-z])(?=.*[!@#$%^&+=]).*$")) {
            throw new IllegalArgumentException("비밀번호 형식에 맞지 않습니다.");
        }

        if (password.contains(username)) {
            throw new IllegalArgumentException("비밀번호에 username을 사용할 수 없습니다.");
        }

        return true;
    }
}
