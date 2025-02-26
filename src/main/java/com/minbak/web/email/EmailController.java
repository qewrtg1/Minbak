package com.minbak.web.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/send")
    public String sendEmail() {
        return "email/email";
    }

    @PostMapping("/send")
    public String sendEmail(@ModelAttribute EmailDto emailDto) {
        emailService.sendMessage(emailDto);
        return "Email sent successfully!";
    }
}
