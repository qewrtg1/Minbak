package com.minbak.web.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    @Autowired
    private RoomsMapper roomsMapper;

//    목록 페이징
    public RoomsPageDto getRooms(int page, int size){
        int offset = (page -1) * size;
        List<RoomsDto> rooms = roomsMapper.selectRoomsWithUser(size,offset);
        int totalElements = roomsMapper.countTotalRooms();
        return new RoomsPageDto(page,size,totalElements,rooms);
    }
    // 상세 보기
    public RoomsDto selectRoomById(int roomId){
        return roomsMapper.selectRoomById(roomId);
    }
    // 수정 기능
    public int updateRoom(RoomsDto roomsDto){
        return roomsMapper.updateRoom(roomsDto);
    }
    // 삭제 기능
    public void deleteRoom(int roomId){
        roomsMapper.deleteRoom(roomId);
    }


}
