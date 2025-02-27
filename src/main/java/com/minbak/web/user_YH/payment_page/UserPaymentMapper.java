package com.minbak.web.user_YH.payment_page;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPaymentMapper {
    PaymentBookDto getPaymentBookDetails(int bookId);
    PaymentRoomDto getPaymentRoomDetails(int roomId);
    PaymentUserDto getPaymentUserDetails(int userId);
    String getRoomImageUrl(int roomId);
}
