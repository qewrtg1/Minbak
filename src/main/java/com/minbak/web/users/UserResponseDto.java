package com.minbak.web.users;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UserResponseDto {
    private Integer userId;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean enabled;
    private String createdAt;
    private String updatedAt;

    //날짜 변환 메서드
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public UserResponseDto(UserDto user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.enabled = user.isEnabled();
        this.createdAt = (user.getCreatedAt() != null) ? user.getCreatedAt().format(formatter) : null;
        this.updatedAt = (user.getUpdatedAt() != null) ? user.getUpdatedAt().format(formatter) : null;
    }
}
