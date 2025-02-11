package com.minbak.web.rooms;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoomsMapper {

    void insertRoom (RoomsDto roomsDto);
    Optional<RoomsDto> selectRoomById(int id);
    List<RoomsDto> selectRoomsByPage(@Param("size") int size, @Param("offset") int offset);
    int countTotalRooms();
    void updateRoom(RoomsDto roomsDto);
    void deleteRoom(int id);
}
