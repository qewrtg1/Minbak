package com.minbak.web.host_today;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HostTodayDto {
    private String roomName;         // 방 이름
    private int userId;              // 사용자 ID
    private LocalDate startDate;          // 예약 시작일
    private LocalDate endDate;            // 예약 종료일
    private int guestsNum;           // 손님 수
    private String status;           // 예약 상태
    private Integer reviewId;        // 리뷰 ID (Pending Reviews를 위한 필드)
    private String roomImageUrl;       // 숙소 이미지 파일경로
}
