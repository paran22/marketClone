package com.example.marketclone.security.handler;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        String errorMsg = "";

        if(exception instanceof UsernameNotFoundException) {
            errorMsg = "존재하지 않는 아이디입니다.";
        } else if(exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호가 잘못 입력되었습니다.";
        }

        if(!StringUtils.isEmpty(errorMsg)) {
            request.setAttribute("errorMsg", errorMsg);
        }

        // 401 에러로 지정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // json 리턴 및 한글깨짐 수정.
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("httpStatus", HttpStatus.UNAUTHORIZED);
        json.put("errorMessage", errorMsg);

        PrintWriter out = response.getWriter();
        out.print(json);
    }
}
