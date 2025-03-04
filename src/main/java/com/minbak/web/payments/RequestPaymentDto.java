package com.minbak.web.payments;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RequestPaymentDto {
    private Integer paymentId;
    private Integer userId;
    private String userEmail;
    private Integer bookId;
    private BigDecimal minAmount; // 최소 금액
    private BigDecimal maxAmount; // 최대 금액
    private String status;
    private String method;
    private String transactionId;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private String roomName;
    private String merchantUid;
    private Integer limit;
    private Integer offset;

}
