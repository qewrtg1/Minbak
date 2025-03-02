package com.minbak.web.host_room;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HostRoomMapper {

    //userId를 전달받아 호스트인지 아닌지를 확인
    boolean isHostUser(@Param("userId") int userId);
    //숙소 목록 조회
    List<HostRoomDTO> findRoomsByHost(@Param("userId") int userId);


}
