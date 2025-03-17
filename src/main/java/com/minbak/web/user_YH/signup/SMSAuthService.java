//package com.minbak.web.user_YH.signup;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SMSAuthService {
//    @Value("${twilio.account_sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth_token}")
//    private String authToken;
//
//    @Value("${twilio.from_phone_number}")
//    private String fromPhoneNumber;
//
//    private String convertToE164(String phoneNumber) {
//        phoneNumber = phoneNumber.replaceAll("[^0-9]", ""); // 숫자만 남기기
//        if (phoneNumber.startsWith("82")) {
//            return "+" + phoneNumber;
//        } else if (phoneNumber.startsWith("0")) {
//            return "+82" + phoneNumber.substring(1);
//        }
//        return phoneNumber; // 이미 국제 형식이면 그대로 반환
//    }
//
//    public void sendSms(String to, String message) {
//        Twilio.init(accountSid, authToken);
//
//        // 전화번호 변환
//        String toE164 = convertToE164(to);
//
//        System.out.println("📨 문자 전송 시도...");
//        System.out.println("✅ 보내는 번호 (FROM): " + fromPhoneNumber);
//        System.out.println("✅ 받는 번호 (TO): " + toE164);
//
//        Message.creator(
//                new com.twilio.type.PhoneNumber(toE164),
//                new com.twilio.type.PhoneNumber(fromPhoneNumber),
//                message
//        ).create();
//
//        System.out.println("✅ 문자 전송 성공!");
//    }
//}
