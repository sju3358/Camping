package com.ssafy.camping.member.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.camping.member.exception.UnAuthorizedException;
import com.ssafy.camping.member.model.service.JwtTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private static final String HEADER_AUTH = "Authorization";

    private final JwtTokenService jwtTokenService;

    @Autowired
    public LoginCheckInterceptor(JwtTokenService jwtTokenService){
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if(request.getMethod().equals("OPTIONS")){
            System.out.println("aaaaaaaa");
            return true;
        }

        final String token = request.getHeader(HEADER_AUTH);

        if (token != null && jwtTokenService.checkToken(token)) {
            return true;
        } else {
            throw new UnAuthorizedException();
        }

    }

}
