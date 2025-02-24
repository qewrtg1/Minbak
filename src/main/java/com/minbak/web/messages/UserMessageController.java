package com.minbak.web.messages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMessageController {

    @GetMapping("/userMessage")
    public String userMessage(){
        return "/message/test";
    }
}
