package com.minbak.web.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HostDto {
    private int hostId;
    private int userId;  // 유저 ID
    private String hobby;
    private String introduction;
    private String isVerified;
    private String accountNumber;
}