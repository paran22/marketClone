package com.example.marketclone.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String email;
    private String password;
    private String name;



}

