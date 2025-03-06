package com.minbak.web.main_page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping
    public String mainPage(){
        return "user-pages/user-main";
    }
}
