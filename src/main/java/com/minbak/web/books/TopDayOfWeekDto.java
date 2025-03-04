package com.minbak.web.books;

import lombok.Data;

@Data
public class TopDayOfWeekDto {
    private String dayOfWeek;
    private int reservationCount;
}
