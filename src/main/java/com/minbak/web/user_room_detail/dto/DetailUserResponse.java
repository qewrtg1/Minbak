package com.minbak.web.user_room_detail.dto;

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
}
