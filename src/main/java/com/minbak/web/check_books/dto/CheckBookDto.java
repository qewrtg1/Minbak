package com.minbak.web.check_books.dto;
import com.minbak.web.payments.PaymentDto;
import com.minbak.web.users.HostDto;
import com.minbak.web.users.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class CheckBookDto {
        private Integer bookId;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer guestsNum;
        private String request;
        private Integer userId;
        private Integer roomId;
        private UserDto user;
        private HostDto host;
        private CheckBookRoomDto room;
        private PaymentDto payment;
        private String roomUrl;
        private String userUrl; //호스트쪽 유저 url
        private List<String> roomUrls;
        private String searchType; // 예약번호, 예약자명, 연락처 등
        private String keyword; // 검색어
        private String dateType; // 체크인, 체크아웃 날짜
        private String statusFilter; // 예약 상태 필터

}
