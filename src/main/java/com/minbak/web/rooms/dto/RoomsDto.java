package com.minbak.web.rooms.dto;

import lombok.Data;

import java.util.List;


@Data
public class RoomsDto {
    private Integer roomId;
    private String title;      // 숙소 제목
    private String name;       // 숙박 시설 이름
    private String content;    // 숙박 시설 설명
    private String address;    // 숙박 시설 주소
    private int price;         // 숙박 가격
    private String useGuide;   // 숙박 이용 안내
    private double latitude;   // 위도
    private double longitude;  // 경도
    private int reviewCount;   // 리뷰 수
    private int maxGuests;     // 최대 숙박 인원
    private Integer userId;    // 등록한 사용자 ID
    private String userName;   // 사용자의 이름
    private String categoryNames;
    private List<String> imageUrls;
}
