package com.minbak.web.payments;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDto {

    private Integer paymentId;
    private Integer userId;
    private Integer bookId;
    private BigDecimal amount;
    private String status;
    private String method;
    private String transactionId;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private String roomName;
    private String merchantUid;


}
