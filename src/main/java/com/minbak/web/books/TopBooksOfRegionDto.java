package com.minbak.web.books;

import lombok.Data;

@Data
public class TopBooksOfRegionDto {
    private String region;
    private int reservationCount;
}
