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
}
