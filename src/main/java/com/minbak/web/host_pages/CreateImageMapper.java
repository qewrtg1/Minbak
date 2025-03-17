package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.CreateImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CreateImageMapper {
    void insertImage(CreateImageDto createImageDto);

    List<CreateImageDto> getImagesByRoomId(@Param("roomId") Integer roomId);
}
