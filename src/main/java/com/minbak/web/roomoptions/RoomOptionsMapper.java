package com.minbak.web.roomoptions;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoomOptionsMapper {

    // 1. 전체 편의시설 조회
    List<RoomOptionsDto> findAllRoomOptions();

    // 2. 특정 ID의 편의시설 조회
    RoomOptionsDto findRoomOptionById(@Param("optionId") int optionId);

    // 3. 새 편의시설 삽입
    int createRoomOption(RoomOptionsDto roomOptionsDto);

    // 4. 편의시설 정보 업데이트
    int updateRoomOption(RoomOptionsDto roomOptionsDto);

    // 5. 편의시설 삭제
    int deleteRoomOption(@Param("optionId") int optionId);


    //  모든 숙소 조회 (편의시설 필터 없이)
    List<RoomOptionsDto> getAllRoomOption();
    // 6. 사용자가 선택한 편의시설을 포함하는 숙소 리스트 조회
    List<RoomOptionsDto> getRoomsByAmenities(@Param("amenities") List<String> amenities, @Param("size") int size);

}
