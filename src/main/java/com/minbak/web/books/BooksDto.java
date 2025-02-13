package com.minbak.web.books;

import lombok.Data;

import java.util.Date;

@Data
public class BooksDto {
    private Integer bookId;
    private String status;
    private Date startDate;
    private Date endDate;
    private Integer guestNum;
    private String request;
    private Integer userId;
    private Integer roomId;
}
