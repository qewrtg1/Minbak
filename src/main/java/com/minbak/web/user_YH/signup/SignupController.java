package com.minbak.web.user_YH.signup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String SignupPage(){
        return "user-pages/user-signup";
    }
}
