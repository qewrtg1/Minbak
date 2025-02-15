package com.minbak.web.spring_security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class RefreshTokenDto {

    private Integer tokenId;
    private String username;
    private String refreshToken;
    private Timestamp expiration;

}
