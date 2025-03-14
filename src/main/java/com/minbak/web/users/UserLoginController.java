package com.minbak.web.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "user-pages/user-login";
    }

    @GetMapping("/oauth2/redirect")
    public String goToMainPg(){
        return "user-pages/redirect-page";
    }
}
