package com.minbak.web.user_room_detail.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailRoomResponse {
    private Long roomId;
    private String title;
    private String content;
    private String address;
    private double latitude;
    private double longitude;
    private int pricePerNight;
    private int maxGuests;
    private int bedrooms;
    private int beds;
    private int bathrooms;
    private double rating;
    private int reviewCount;
    private String buildingType;
    private String useGuide;
    private List<String> imageUrls; // 숙소 이미지 리스트
    private List<String> options; // 숙소 편의시설 리스트
}
