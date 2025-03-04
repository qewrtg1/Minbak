package com.minbak.web.roomoptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomOptionsService {

    private final RoomOptionsMapper roomOptionsMapper;

    @Autowired
    public RoomOptionsService(RoomOptionsMapper roomOptionsMapper) {
        this.roomOptionsMapper = roomOptionsMapper;
    }

    // 1. 전체 편의시설 조회
    public List<RoomOptionsDto> getAllRoomOptions() {
        return roomOptionsMapper.findAllRoomOptions();
    }

    // 2. 특정 편의시설 조회 (ID 기준)
    public RoomOptionsDto getRoomOptionById(int optionId) {
        return roomOptionsMapper.findRoomOptionById(optionId);
    }

    // 3. 새 편의시설 추가
    public int addRoomOption(RoomOptionsDto roomOptionsDto) {
        return roomOptionsMapper.createRoomOption(roomOptionsDto);
    }

    // 4. 편의시설 정보 업데이트
    public int updateRoomOption(RoomOptionsDto roomOptionsDto) {
        return roomOptionsMapper.updateRoomOption(roomOptionsDto);
    }

    // 5. 편의시설 삭제
    public int deleteRoomOption(int optionId) {
        return roomOptionsMapper.deleteRoomOption(optionId);
    }


    // 다대다 기능으로 필요 없을 걸로 추정
    // 사용자가 선택한 편의시설을 포함하는 숙소 리스트 조회
    public List<RoomOptionsDto> getRoomsByAmenities(List<String> amenities) {
        if (amenities == null || amenities.isEmpty()) {
            return roomOptionsMapper.getAllRoomOption();
        }
        return roomOptionsMapper.getRoomsByAmenities(amenities, amenities.size());
    }

}
