package com.minbak.web.user_YH.payment_page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPaymentService {

    @Autowired
    private UserPaymentMapper userPaymentMapper;

    public PaymentBookDto getPaymentBookDetails(int bookId) {
        return userPaymentMapper.getPaymentBookDetails(bookId);
    }

    public PaymentRoomDto getPaymentRoomDetails(int roomId) {
        return userPaymentMapper.getPaymentRoomDetails(roomId);
    }

    public PaymentUserDto getPaymentUserDetails(int userId) {
        return userPaymentMapper.getPaymentUserDetails(userId);
    }

    public String getRoomImageUrl(int roomId){
        return userPaymentMapper.getRoomImageUrl(roomId);
    }
}