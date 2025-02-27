package com.minbak.web.user_YH.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class DetailBookResponse {
    private Long bookId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int guests;
    private int totalPrice;
    private String status;
}
