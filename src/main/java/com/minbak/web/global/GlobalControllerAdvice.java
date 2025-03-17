package com.minbak.web.global;

import com.minbak.web.main_page.MainPageService;
import com.minbak.web.user_YH.dto.DetailUserResponse;
import com.minbak.web.users.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Objects;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    UsersService usersService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {

        String requestURI = request.getRequestURI(); // 현재 요청된 URL 가져오기
        String referer = request.getHeader("referer");

        // 요청된 컨트롤러가 @RestController인지 확인하여 제외
        Object handler = request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");
        if (handler != null && handler.getClass().getAnnotation(org.springframework.web.bind.annotation.RestController.class) != null) {
            return; // RestController라면 실행하지 않음
        }

        // 특정 URL에서는 실행되지 않도록 예외 처리
        if (requestURI.startsWith("/admin") || requestURI.startsWith("/error")) {
            return; // 특정 URL에서는 실행되지 않음
        }

        System.out.println("Referer(이전 페이지): " + referer);
        System.out.println(requestURI);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && !Objects.equals(authentication.getName(), "anonymousUser")){
            Integer userId = usersService.findUserIdByEmail(authentication.getName());
            DetailUserResponse detailUserResponse = usersService.getUserInfo(userId);

            if(usersService.findHostByUserId(userId) != null){
                detailUserResponse.setIsHost(true);
            }

            model.addAttribute("headerUser",detailUserResponse);

        }else {
            model.addAttribute("headerUser",usersService.getUserInfo(0));
        }
    }
}
