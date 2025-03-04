package com.minbak.web.payments;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDto {

    private Integer paymentId;
    private Integer userId;
    private String userEmail;
    private Integer bookId;
    private BigDecimal amount;
    private String status;
    private String method;
    private String transactionId;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private String roomName;
    private String merchantUid;
    private LocalDate startDate;
    private LocalDate endDate;
    private long dateDifference;


}
