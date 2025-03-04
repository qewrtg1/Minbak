package com.minbak.web.check_books.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class CheckBookHostDto {



        private int hostId;
        private int userId;  // 유저 ID
        private String hobby;
        private String introduction;
        private String isVerified;
        private String accountNumber;
        private LocalDate createdAt; //로컬데이트 타입으로 저장, 시간단위안나옴

}
