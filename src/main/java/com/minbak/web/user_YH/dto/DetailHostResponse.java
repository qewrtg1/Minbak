package com.minbak.web.user_YH.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailHostResponse {
    private Integer hostId;
    private String name;
    private String imageUrl; // 호스트 프로필 사진
    private int reviewCount;
    private double rating;
    private int yearsOfExperience;
    private String hobby;
    private String introduction;
}
