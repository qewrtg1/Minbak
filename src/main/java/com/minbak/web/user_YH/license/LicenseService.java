package com.minbak.web.user_YH.license;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseMapper licenseMapper;

    // 영업신고증 추가
    public void addBusinessLicense(LicenseDto licenseDto) {
        licenseMapper.insertBusinessLicense(licenseDto);
    }

    // 특정 호스트의 영업신고증 조회
    public LicenseDto getBusinessLicenseByHostId(int hostId) {
        return licenseMapper.getBusinessLicenseByHostId(hostId);
    }

    // 모든 영업신고증 목록 조회
    public List<LicenseDto> getAllBusinessLicenses() {
        return licenseMapper.getAllBusinessLicenses();
    }

    // 영업신고증 수정
    public void updateBusinessLicense(LicenseDto licenseDto) {
        licenseMapper.updateBusinessLicense(licenseDto);
    }

    // 영업신고증 삭제
    public void deleteBusinessLicense(int licenseId) {
        licenseMapper.deleteBusinessLicense(licenseId);
    }

    // user_id로 host_id 조회
    public Integer getHostIdByUserId(int userId) {
        return licenseMapper.getHostIdByUserId(userId);
    }

    public Integer getUserIdByHostId(int hostId) {
        return licenseMapper.getHostIdByUserId(hostId);
    }

    // 특정 호스트의 영업신고증 조회
    public LicenseDto getLicenseByHostId(int hostId) {
        return licenseMapper.getLicenseByHostId(hostId);
    }

    // 영업신고증 삭제
    @Transactional
    public void deleteLicenseByHostId(int hostId) {
        licenseMapper.deleteLicenseByHostId(hostId);
    }
}
