package com.minbak.web.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private final EmailService emailService;

    @GetMapping("/email")
    public String sendMail() {
        return "email/email";
    }

    @PostMapping("/email")
    public void execMail(EmailDto emailDto) {
//        EmailDto emailDto1 = new EmailDto();
//        emailDto1.setAddress("tyuy1018@gmail.com");
//        emailDto1.setTitle("메일 전송 테스트입니다");
//        emailDto1.setMessage("메일 잘 가셨나요?");
        emailService.execMail(emailDto);
    }
}
