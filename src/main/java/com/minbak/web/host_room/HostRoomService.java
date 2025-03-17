package com.minbak.web.host_room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // @Autowired 대신 사용
public class HostRoomService {

    private final HostRoomMapper hostRoomMapper;

    // 숙소 목록 조회
    public List<HostRoomDTO> getRoomsByHost(int userId) {
        return hostRoomMapper.findRoomsByHost(userId);
    }

    public HostRoomDTO findRoomById(Integer roomId) {
        return hostRoomMapper.findRoomById(roomId); // 기존 숙소 정보 불러오기
    }


//    public void addRoom(HostRoomDTO hostRoomDTO) {
//        hostRoomMapper.insertHostRoom(hostRoomDTO);
//    }
//    public void updateRoom(HostRoomDTO hostRoomDTO) {
//        hostRoomMapper.updateHostRoom(hostRoomDTO); // 업데이트 실행
//    }
//    public void deleteRoom(int roomId) {  // 추가됨
//        hostRoomMapper.deleteHostRoom(roomId);
//    }

}
