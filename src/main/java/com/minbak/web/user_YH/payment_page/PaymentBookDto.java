package com.minbak.web.user_YH.payment_page;


import lombok.Data;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class PaymentBookDto {
    private int bookId;
    private int userId;
    private int roomId;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestsNum;

    public int getNights() {
        if (startDate != null && endDate != null) {
            return (int) ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0;
    }
}
