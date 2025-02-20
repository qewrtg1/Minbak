package com.minbak.web.books.monthly;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class MonthlyBookDto {
    private String roomName;
    private Map<LocalDate, String> status;
}
