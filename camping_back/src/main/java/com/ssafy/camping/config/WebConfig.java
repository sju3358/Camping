package com.ssafy.camping.config;

import com.ssafy.camping.member.interceptor.LoginCheckInterceptor;
import com.ssafy.camping.member.model.mapper.JwtTokenMapper;
import com.ssafy.camping.member.model.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.SQLException;
import java.util.Map;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Autowired
    public WebConfig(LoginCheckInterceptor loginCheckInterceptor){
        this.loginCheckInterceptor = loginCheckInterceptor;
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .order(1)	// 적용할 필터 순서 설정
                .addPathPatterns("/board/**")
                .addPathPatterns("/favorite/**")
                .addPathPatterns("/auth/**")
                .excludePathPatterns("/auth/login")
                .excludePathPatterns("/auth/find-password/*")
                .excludePathPatterns("/auth/register")
                .excludePathPatterns("/naver/**");
    }
}
