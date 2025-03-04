package com.minbak.web.user_YH.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailUserResponse {
    private Long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String profileImageUrl; // 유저 프로필 사진
    private Boolean isHost;
}
