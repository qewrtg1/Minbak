package com.minbak.web.payments;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PaymentInfoDto {
    private String impUid;
    private String merchantUid;
    private String payMethod;
    private String name;
    private int amount;
    private String status;
    private long paidAt;
    private String receiptUrl;

    // JSON 응답을 DTO로 변환하는 정적 메서드 추가
    public static PaymentInfoDto fromMap(Map<String, Object> map) {
        PaymentInfoDto dto = new PaymentInfoDto();
        dto.setImpUid((String) map.get("imp_uid"));
        dto.setMerchantUid((String) map.get("merchant_uid"));
        dto.setPayMethod((String) map.get("pay_method"));
        dto.setName((String) map.get("name"));
        dto.setAmount((int) map.get("amount"));
        dto.setStatus((String) map.get("status"));
        dto.setPaidAt(Long.parseLong(map.get("paid_at").toString()));
        dto.setReceiptUrl((String) map.get("receipt_url"));
        return dto;
    }
}
