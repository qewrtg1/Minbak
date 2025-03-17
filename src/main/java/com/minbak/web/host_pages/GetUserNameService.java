package com.minbak.web.host_pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserNameService {

    @Autowired
    CreateHostMapper createHostMapper;

    public String getUserName(int userId) {
        try {
            return createHostMapper.getUserNameById(userId);
        } catch (Exception e) {
            throw new RuntimeException("사용자 이름 조회 실패: " + e.getMessage(), e);
        }
    }
}
