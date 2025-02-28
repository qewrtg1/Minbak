package com.minbak.web.global;

import com.minbak.web.main_page.MainPageService;
import com.minbak.web.users.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    UsersService usersService;

    @Autowired
    MainPageService mainPageService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {
        String requestURI = request.getRequestURI(); // 현재 요청된 URL 가져오기

        // 특정 URL에서는 실행되지 않도록 예외 처리
        if (requestURI.startsWith("/admin")) {
            return; // 특정 URL에서는 실행되지 않음
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null){
            int userId = usersService.findUserIdByEmail(authentication.getName());

            model.addAttribute("user",usersService.getUserInfo(userId));

        }else {
            model.addAttribute("user",usersService.getUserInfo(0));
        }
    }
}
