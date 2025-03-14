package com.minbak.web.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/password")
@RequiredArgsConstructor
public class PasswordResetController {

    @Autowired
    private final PasswordResetService passwordResetService;

    @GetMapping("/reset")
    public String resetPassword() {
        return "user-pages/find-password";
    }

    @PostMapping("/reset")
    @ResponseBody
    public String resetPassword(@RequestParam String email) {
        try {
            passwordResetService.sendTemporaryPassword(email);
            return "임시 비밀번호가 이메일로 전송되었습니다.";
        } catch (MessagingException e) {
            return "이메일 전송 중 오류가 발생했습니다.";
        }
    }
}
