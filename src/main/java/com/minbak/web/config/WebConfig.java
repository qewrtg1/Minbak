package com.minbak.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://ec2-13-124-222-6.ap-northeast-2.compute.amazonaws.com:8080") // '/' 제거
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS") // 명시적으로 설정
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }

//    윈도우용

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/uploads/**") // 요청 URL
//                .addResourceLocations("file:C:/img/uploads/"); // 실제 파일이 저장된 디렉토리
//    }


//    리눅스용

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**") // 요청 URL
                .addResourceLocations("file:/home/ubuntu/img/uploads/"); // 실제 파일이 저장된 디렉토리
    }
}
