package com.minbak.web.user_YH.host_edit_room.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HostRoomEditDto {
    private int roomId;          // 숙소 ID
    private String name;         // 숙소 이름
    private String content;      // 숙소 설명
    private int price;           // 가격
    private int maxGuests;       // 최대 숙박 인원
    private int bedrooms;        // 침실 개수
    private int beds;            // 침대 개수
    private int bathrooms;       // 욕실 개수
    private String buildingType; // 건물 유형 (아파트, 주택, 빌라 등)
    private String useGuide;     // 이용 가이드
    private double latitude;     // 위도
    private double longitude;    // 경도
    private Boolean isActive;    // 숙소 활성화 상태
    private List<String> categories; // 숙소 카테고리 리스트
    private List<String> options;    // 숙소 옵션 리스트
    private List<String> images;     // 숙소 이미지 URL 리스트
}
