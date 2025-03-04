package com.minbak.web.check_books.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckBookRoomDto {
    private int roomId;              // 숙소 고유 ID
    private String name;
    private String content;      // 숙소 설명
    private String address;      // 숙소 주소
    private Integer price;             // 숙소 가격
    private String useGuide;    // 숙소 이용 안내
    private double latitude;          // 숙소 위도 y
    private double longitude;         // 숙소 경도 x
    private Integer userId;
    private String userEmail;
    private Integer reviewCount;
    private Integer maxGuests;
    private Integer bedrooms;
    private Integer beds;
    private Integer bathrooms;
    private String buildingType;


    }
