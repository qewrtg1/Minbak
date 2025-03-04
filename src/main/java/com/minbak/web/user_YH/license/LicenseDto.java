package com.minbak.web.user_YH.license;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LicenseDto {
    private int licenseId;
    private int hostId;
    private String businessType;
    private String region;
    private LocalDateTime uploadedAt; // MyBatis에서 자동 매핑됨
    private String licenseFileUrl;
}
