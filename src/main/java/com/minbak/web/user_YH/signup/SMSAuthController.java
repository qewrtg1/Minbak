package com.minbak.web.user_YH.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class SMSAuthController {

    private final SMSAuthService authService;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @PostMapping("/send-sms")
    public ResponseEntity<Map<String, Object>> sendSms(@RequestParam String phoneNumber) {
        String code = generateCode(); // 인증번호 생성
        verificationCodes.put(phoneNumber, code); // 인증번호 저장

        authService.sendSms(phoneNumber, "회원가입 인증번호: " + code);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "인증번호가 전송되었습니다.");
        return ResponseEntity.ok(response);
    }

    // ✅ 인증번호 검증
    @PostMapping("/verify-sms")
    public ResponseEntity<Map<String, Object>> verifySms(@RequestParam String phoneNumber, @RequestParam String code) {
        Map<String, Object> response = new HashMap<>();

        if (verificationCodes.containsKey(phoneNumber) && verificationCodes.get(phoneNumber).equals(code)) {
            verificationCodes.remove(phoneNumber);
            response.put("success", true);
            response.put("message", "인증 성공!");
        } else {
            response.put("success", false);
            response.put("message", "인증번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    // ✅ 랜덤 인증번호 생성 (6자리)
    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}

