package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.CreateImageDto;
import com.minbak.web.host_pages.dto.HostDto;
import com.minbak.web.rooms.RoomsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CreateHostMapper {
    void insertRoom(HostDto hostDto);

    // ✅ 이제 HostDto에서 바로 imageFiles 리스트를 참조
    void insertRoomImages(@Param("hostDto") HostDto hostDto,@Param("roomId") int roomId);

    // ✅ 한 번에 여러 개의 옵션 저장 (List<Integer> 사용)
    void insertRoomOptions(@Param("roomId") int roomId, @Param("optionIds") List<Integer> optionIds);

    String getUserNameById(@Param("userId") int userId);

    void updateRoomImages(String fileUrl, int roomId);

    void insertRoomCategories(@Param("roomId") int roomId, @Param("categoryIds") List<Integer> categoryIds);
}
