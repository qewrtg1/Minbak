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
    public RoomsPageDto getRooms(String keyword,int page, int size){

        // 페이지 오프셋 계산
        page = Math.max(1, page);
        int offset = Math.max(0, (page - 1) * size);

        // 검색어가 있을 경우
        List<RoomsListDto> rooms;
        int totalElements;// 검색어가 없으면 null로 처리
        if (keyword != null && !keyword.isEmpty()) {
            // 검색어에 맞는 방 목록 가져오기
            rooms = roomsMapper.selectRoomNames(keyword, offset, size);
            // 검색어에 맞는 방 개수 가져오기
            totalElements = roomsMapper.selectTotalRoomsCount(keyword);
        } else {
            // 검색어가 없을 경우 모든 방을 가져옴
            rooms = roomsMapper.selectAllRooms(offset, size);
            // 모든 방의 개수 가져오기
            totalElements = roomsMapper.selectTotalRoomsCount(null);
        }
        return new RoomsPageDto(page, size, totalElements, rooms);
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
