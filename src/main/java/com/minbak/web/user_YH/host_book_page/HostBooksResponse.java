package com.minbak.web.user_YH.host_book_page;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HostBooksResponse {
    private int bookId;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String request;
    private int guestsNum;

    private int guestId;
    private String guestName;
    private String guestEmail;
    private String guestPhone;

    private int roomId;
    private String roomTitle;
    private String roomAddress;
    private int roomPrice;
    private String buildingType;
    private int maxGuests;
    private String roomImageUrl;
}
