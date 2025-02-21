package com.minbak.web.rooms.dto;

import lombok.Data;

@Data
public class RoomsListDto {
    private Integer roomId;       // 숙박 시설 고유 ID
    private String name;          // 숙박 시설 이름
    private String title;         // 숙소 제목
    private String address;       // 숙박 시설 주소
    private Integer price;        // 숙박 가격
    private Integer reviewCount;  // 리뷰 수 (나중에 리뷰 테이블을 참조하게 수정 필요)
    private String userName;          // 등록한 사용자 ID
}
