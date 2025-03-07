package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.HostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HostService implements HostServiceInterface{

    @Autowired
    private CreateHostMapper createHostMapper;

    @Transactional
    @Override
    public int insertRoom(HostDto hostDto) {
        // 🏡 1. 숙소 정보 `rooms` 테이블에 저장
        createHostMapper.insertRoom(hostDto);
        int roomId = hostDto.getRoomId(); // MyBatis에서 자동 생성된 키 가져오기

        // 🖼️ 2. 이미지 저장 (`image_files` 테이블) - ✅ 최적화: 한 번의 SQL 실행으로 저장
        if (hostDto.getImageFiles() != null && !hostDto.getImageFiles().isEmpty()) {
            hostDto.getImageFiles().forEach(image -> image.setEntityId(roomId)); // roomId 설정
            createHostMapper.insertRoomImages(hostDto);
        }

        // 🎛️ 3. 옵션 저장 (`rooms_room_options` 테이블) - ✅ 최적화: 한 번의 SQL 실행으로 저장
        if (hostDto.getOptionIds() != null && !hostDto.getOptionIds().isEmpty()) {
            createHostMapper.insertRoomOptions(roomId, hostDto.getOptionIds());
        }

        return roomId;
    }
}
