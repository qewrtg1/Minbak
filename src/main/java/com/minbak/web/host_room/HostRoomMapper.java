package com.minbak.web.host_room;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HostRoomMapper {
    //숙소 목록 조회
    List<HostRoomDTO> findRoomsByHost(@Param("userId") int userId);
    // 숙소 생성
    void insertHostRoom(HostRoomDTO hostRoomDTO);
    // 특정 숙소 정보 불러오기 (수정 시 사용)
    HostRoomDTO findRoomById(@Param("roomId") Integer roomId);
    // 숙소 정보 수정
    void updateHostRoom(HostRoomDTO hostRoomDTO);
    // 숙소 삭제
    void deleteHostRoom(@Param("roomId") int roomId);


}
