package com.minbak.web.payments;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentDto {

    private Integer paymentId;
    private int userId;
    private int bookId;
    private double amount;
    private String status;
    private String method;
    private String transactionId;
    private Date paidAt;
    private Date createdAt;

}