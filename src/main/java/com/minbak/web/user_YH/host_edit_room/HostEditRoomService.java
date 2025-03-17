package com.minbak.web.user_YH.host_edit_room;

import com.minbak.web.user_YH.host_edit_room.dto.HostRoomEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostEditRoomService {

    private final HostEditRoomMapper hostEditRoomMapper;

    // 숙소 활성화 상태 변경
    public void toggleRoomActiveStatus(int roomId, boolean isActive) {
        hostEditRoomMapper.updateRoomActiveStatus(roomId, isActive);
    }

    // 특정 숙소의 활성화 상태 가져오기
    public boolean getRoomActiveStatus(int roomId) {
        return hostEditRoomMapper.getRoomActiveStatus(roomId);
    }

    public HostRoomEditDto getHostRoomData(int roomId) {
        // 숙소 기본 정보 가져오기
        HostRoomEditDto room = hostEditRoomMapper.getHostRoomById(roomId);

        if (room != null) {
            // 카테고리, 옵션, 이미지 리스트 추가
            List<String> categories = hostEditRoomMapper.getRoomCategories(roomId);
            List<String> options = hostEditRoomMapper.getRoomOptions(roomId);
            List<String> images = hostEditRoomMapper.getRoomImages(roomId);

            room.setCategories(categories);
            room.setOptions(options);
            room.setImages(images);
        }

        return room;
    }

    // roomId를 이용해 호스트 userId 조회
    public int getHostUserIdByRoomId(int roomId) {
        return hostEditRoomMapper.getHostUserIdByRoomId(roomId);
    }

    public boolean getHostIsVerifiedByRoomId(int roomId){
        return hostEditRoomMapper.getHostIsVerifiedByRoomId(roomId);
    }
}
