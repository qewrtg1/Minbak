package com.minbak.web.dash_board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyReservationDto {
    private String month;  // "YYYY-MM" 형식 (예: 2024-02)
    private int bookCount; // 해당 월의 예약 건수
}