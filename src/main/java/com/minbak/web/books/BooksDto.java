package com.minbak.web.books;

import com.minbak.web.file_upload.ImageFileDto;
import com.minbak.web.payments.PaymentDto;
import com.minbak.web.rooms.RoomsDto;
import com.minbak.web.users.UserDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BooksDto {
    private Integer bookId;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer guestsNum;
    private String request;
    private Integer userId;
    private Integer roomId;
    private UserDto user;
    private RoomsDto room;
    private PaymentDto payment;
    private ImageFileDto file;
    private Integer hostId;

    private String userName;
    private String email;
    private String phoneNumber;
    private String roomName;

    private String searchType; // 예약번호, 예약자명, 연락처 등
    private String keyword; // 검색어
    private String dateType; // 체크인, 체크아웃 날짜
    private String statusFilter; // 예약 상태 필터
}
