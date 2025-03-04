package com.minbak.web.user_YH.license;

import org.apache.ibatis.annotations.*;

        import java.util.List;

@Mapper
public interface LicenseMapper {

    // 영업신고증 추가
    void insertBusinessLicense(LicenseDto License);

    // 특정 호스트의 영업신고증 조회
    LicenseDto getBusinessLicenseByHostId(int hostId);

    // 모든 영업신고증 목록 조회
    List<LicenseDto> getAllBusinessLicenses();

    // 영업신고증 수정
    void updateBusinessLicense(LicenseDto License);

    // 영업신고증 삭제
    void deleteBusinessLicense(int licenseId);

    Integer getHostIdByUserId(int userId);

    void deleteLicenseByHostId(int hostId);

    LicenseDto getLicenseByHostId(int hostId);
}
