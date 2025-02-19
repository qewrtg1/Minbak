package com.minbak.web.rooms;

import com.minbak.web.rooms.dto.RoomsDto;
import com.minbak.web.rooms.dto.RoomsListDto;
import com.minbak.web.rooms.dto.RoomsPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    @Autowired
    private final RoomsMapper roomsMapper;

    public RoomsService(RoomsMapper roomsMapper) {
        this.roomsMapper = roomsMapper;
    }

    //    목록 페이징
    public RoomsPageDto getRooms(int page, int size){
        int offset = (page -1) * size;
        int totalElements = roomsMapper.selectTotalRoomsCount();
        List<RoomsListDto> rooms = roomsMapper.selectRoomNames(offset,size);

        return new RoomsPageDto(page,size,totalElements,rooms);
    }
    // 상세 보기
    public RoomsDto getRoomList(int roomId){
        return roomsMapper.getRoomsList(roomId);
    }
    // 수정 페이지
    public RoomsDto getRoomById(int roomId){
        return roomsMapper.getRoomById(roomId);
    }
    // 수정 기능
    public void updateRoom(RoomsDto roomsDto){
        roomsMapper.updateRoom(roomsDto);
    }
    // 삭제 기능
    public void deleteRoom(int roomId){
        roomsMapper.deleteRoom(roomId);
    }



}
