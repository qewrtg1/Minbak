package com.minbak.web.main_page.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainRoomDto {

    private int roomId; // 숙소의 고유 ID (Primary Key)
    private String name; // 숙소의 이름
    private String address; // 숙소의 주소 (지역 정보 포함)
    private int price; // 숙소 1박당 가격 (단위: 원)
    private String title; // 숙소의 간단한 소개 또는 제목
    private int reviewCount; // 해당 숙소의 총 리뷰 개수
    private float reviewScore; // 해당 숙소의 평균 리뷰 점수 (0~5점 사이, 소수점 포함)
    private MainImageFileDto images; // 숙소에 등록된 이미지 파일 목록 (최대 5개 정도 가능)
    private MainReviewDto recentReview; // 해당 숙소에 대한 가장 최근 리뷰 정보

}