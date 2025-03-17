package com.minbak.web.user_YH.signup;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SMSAuthService {
    private final DefaultMessageService messageService;
    private final String senderNumber = "010-7720-5232"; // 발신자 번호

    public SMSAuthService() {
        // ✅ Coolsms API 초기화
        this.messageService = NurigoApp.INSTANCE.initialize("NCS6Z8G5S8I33MXO", "REHSRBYAZFWSVODPTXYQU27NZE8LBPLZ", "https://api.coolsms.co.kr");
    }

    // 인증번호 전송
    public String sendVerificationCode(String phoneNumber) {
        String verificationCode = generateCode();

        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(phoneNumber);
        message.setText("[Web] 인증번호: " + verificationCode);

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println("SMS 전송 성공: " + response);
            return verificationCode;
        } catch (Exception e) {
            System.err.println("SMS 전송 실패: " + e.getMessage());
            return null;
        }
    }

    // 랜덤 6자리 인증번호 생성
    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 숫자 생성
        return String.valueOf(code);
    }
}
