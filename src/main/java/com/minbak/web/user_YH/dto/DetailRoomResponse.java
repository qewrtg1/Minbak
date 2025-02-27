package com.minbak.web.user_YH.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DetailRoomResponse {
    private Integer roomId;
    private String name;
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
    private Map<String, List<String>> options; // 숙소 편의시설 리스트
}
