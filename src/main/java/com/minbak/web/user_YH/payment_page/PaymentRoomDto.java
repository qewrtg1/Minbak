package com.minbak.web.user_YH.payment_page;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRoomDto {
    private int roomId;
    private String title;
    private String buildingType;
    private BigDecimal price;
    private String imageUrl;
    private int maxGuests;
    private double rating;
    private int reviewCount;
}
