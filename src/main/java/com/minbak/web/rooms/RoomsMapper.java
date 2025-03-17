package com.minbak.web.rooms;

import com.minbak.web.rooms.dto.RoomsDto;
import com.minbak.web.rooms.dto.RoomsListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoomsMapper {

    // rooms 의 디테일
    RoomsDto getRoomsList(int roomId);
    List<String> getRoomImages(@Param("roomId") int roomId);


    // rooms 의 전체 리스트
    List<RoomsListDto> selectRoomNames(@Param("keyword") String keyword,@Param("offset") int offset, @Param("size") int size);
    int selectTotalRoomsCount(String keyword);

    // 검색어가 없는 경우 모든 방 목록
    List<RoomsListDto> selectAllRooms(@Param("offset") int offset, @Param("size") int size);

    // 수정 페이지
    RoomsDto getRoomById(int roomId);

    // 수정 기능
    void updateRoom(RoomsDto roomsDto);
    void deleteRoom(int roomId);
    //void insertRoom (RoomsDto roomsDto);
    //RoomsDto selectRoomById(int id);
    //RoomsDto selectRoomByIdWithUser(int roomId);
    //List<RoomsDto> selectRoomsByPage(@Param("size") int size, @Param("offset") int offset);
    //int countTotalRooms();
    //int updateRoom(RoomsDto roomsDto);
    //void deleteRoom(int id);
    //List<RoomsDto> selectRoomsWithUser(@Param("size") int size, @Param("offset") int offset);
    // 예약된 객실의 room_id를 -1로 업데이트하는 메서드
    //void updateRoomForDelete(int roomId);

    // 객실을 삭제하는 메서드
    //void deleteRoom(int roomId);

}
