package com.minbak.web.messages;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestMessageFilterDto {


        private Integer messageId;
        private Integer userId;
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
        private String keyword;
        private LocalDate startDate;
        private LocalDate endDate;

}
