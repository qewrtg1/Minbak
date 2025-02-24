package com.minbak.web.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
//날짜정보를 받아서 string으로 변환하는 코드
public class UserResponseDto {
    private Integer userId;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean enabled;
    private LocalDateTime createdAt;  // 계정 생성일
    private LocalDateTime updatedAt;  // 계정 수정일
    private Integer BookCount;

}
