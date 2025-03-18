package com.minbak.web.schedular;

import com.minbak.web.email.EmailDto;
import com.minbak.web.email.EmailService;
import com.minbak.web.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailSchedular {

    private final EmailService emailService;

    private final ReviewService reviewService;

    @Scheduled(cron = "0 00 12 * * *") // 이메일 전송 시간 설정 (초 분 시, 일 월 요일 순) - 매일 12:00에 메일 전송
    public void printDate(){
        List<EmailDto> emailList = reviewService.setEmail();
        for(EmailDto emailDto : emailList) {
            emailService.sendMessage(emailDto);
        }
    }
}