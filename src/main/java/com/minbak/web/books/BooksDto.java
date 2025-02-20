package com.minbak.web.books;

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

    private String searchType; // 예약번호, 회원아이디 등
    private String keyword; // 검색어
    private String dateType; // 예약일, 체크인 등
    private String statusFilter; // 예약 상태 필터
}
