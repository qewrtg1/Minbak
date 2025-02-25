package com.minbak.web.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String myAddress;

    public void execMail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getAddress());
        message.setFrom(myAddress);
        message.setSubject(emailDto.getTitle());
        message.setText(emailDto.getMessage());
        javaMailSender.send(message);
    }
}
