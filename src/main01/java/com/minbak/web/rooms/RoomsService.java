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
}
