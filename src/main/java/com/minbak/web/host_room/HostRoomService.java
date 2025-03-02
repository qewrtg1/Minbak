package com.minbak.web.host_room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // @Autowired 대신 사용
public class HostRoomService {

    private final HostRoomMapper hostRoomMapper;

    public boolean isHost(int userId) {
        return hostRoomMapper.isHostUser(userId);
    }
    public List<HostRoomDTO> getRoomsByHost(int userId) {
        return hostRoomMapper.findRoomsByHost(userId);
    }
}
