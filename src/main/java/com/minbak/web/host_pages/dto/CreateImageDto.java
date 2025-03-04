package com.minbak.web.host_pages.dto;

import lombok.Data;

@Data
public class CreateImageDto {
    private Integer imageId;  // 이미지 고유 ID
    private String fileUrl;   // 이미지 URL
    private String fileName;  // 파일 이름
    private Integer userId;   // 업로드한 사용자 ID
    private String entityType; // 'rooms' (숙소), 'users' (사용자 프로필) 등
    private Integer entityId; // 숙소 ID (rooms 테이블과 연결)
}
