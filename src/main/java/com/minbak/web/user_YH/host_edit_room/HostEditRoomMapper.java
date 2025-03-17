package com.minbak.web.user_YH.host_edit_room;

import com.minbak.web.user_YH.host_edit_room.dto.HostRoomEditDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HostEditRoomMapper {

    // 숙소 활성화 상태 변경
    void updateRoomActiveStatus( int roomId, boolean isActive);

    // 특정 숙소 활성화 상태 조회
    boolean getRoomActiveStatus( int roomId);

    // 숙소 기본 정보 가져오기
    HostRoomEditDto getHostRoomById(int roomId);

    // 숙소 카테고리 가져오기
    List<String> getRoomCategories(int roomId);

    // 숙소 옵션 가져오기
    List<String> getRoomOptions(int roomId);

    // 숙소 이미지 가져오기
    List<String> getRoomImages(int roomId);

    // roomId로 호스트(user_id) 조회
    int getHostUserIdByRoomId(int roomId);

    //해당 room의 유저가 isVerified인지
    boolean getHostIsVerifiedByRoomId(int roomId);

}
