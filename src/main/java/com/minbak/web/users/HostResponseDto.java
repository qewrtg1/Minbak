package com.minbak.web.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HostResponseDto {
    private Integer userId;               // 유저 고유 ID
    private String name;                  // 유저 이름
    private String email;                 // 유저 이메일
    private String phoneNumber;           // 유저 전화번호
    private Boolean userEnabled;          // 유저 활성화 여부
    private Integer hostId;               // 호스트 고유 ID
    private String hobby;                 // 호스트 취미
    private String introduction;          // 호스트 소개
    private String isVerified;           // 호스트 인증 여부
    private String accountNumber;         // 계좌번호
    private LocalDateTime createdAt;  // 호스트 가입일
    private LocalDateTime updatedAt;  // 호스트 수정일
    private Integer roomCount;            // 관리하는 숙소 개수
}
