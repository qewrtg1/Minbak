package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.HostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HostService {

    @Autowired
    private CreateHostMapper createHostMapper;

    @Transactional
    public Integer insertRoom(HostDto hostDto) {
        try {
            createHostMapper.insertRoom(hostDto);
            return hostDto.getRoomId(); // MyBatis에서 자동 생성된 roomId 반환
        } catch (Exception e) {
            throw new RuntimeException("숙소 등록 중 오류 발생: " + e.getMessage(), e);
        }
    }

    public String getUserName(int userId) {
        try {
            return createHostMapper.getUserNameById(userId);
        } catch (Exception e) {
            throw new RuntimeException("사용자 이름 조회 실패: " + e.getMessage(), e);
        }
    }
}
