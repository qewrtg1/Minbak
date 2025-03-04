package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.OptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomOptionMapper {

    void insertRoomOption(@Param("roomId") Integer roomId, @Param("optionId") Integer optionId);

   List<OptionDto> getOptionsByRoomId(@Param("roomId") Integer roomId);

}
