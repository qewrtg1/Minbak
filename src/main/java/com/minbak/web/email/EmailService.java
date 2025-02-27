package com.minbak.web.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    public void sendMessage(EmailDto emailDto) {
        if (emailDto.getTo() == null || emailDto.getTo().trim().isEmpty()) {
            throw new IllegalArgumentException("수신자 이메일 주소가 비어 있습니다!");
        }
        if (emailDto.getTitle() == null || emailDto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일 제목이 비어 있습니다!");
        }
        if (emailDto.getMessage() == null || emailDto.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일 내용이 비어 있습니다!");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getTo());
        message.setSubject(emailDto.getTitle());
        message.setText(emailDto.getMessage());

        try {
            javaMailSender.send(message);
            System.out.println("이메일 전송 성공!");
        } catch (MailException e) {
            System.out.println("[-] 이메일 전송중에 오류가 발생하였습니다 " + e.getMessage());
            e.printStackTrace(); // 오류 로그 출력
            throw new RuntimeException("이메일 전송 실패", e); // 명확한 예외 발생
        }
    }
}
