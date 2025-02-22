package com.minbak.web.dash_board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyRevenueDto {
    private String month;  // "YYYY-MM" 형식
    private int revenue;   // 해당 월의 총 매출
}