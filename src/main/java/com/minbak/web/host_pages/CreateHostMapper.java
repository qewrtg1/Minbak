package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.HostDto;
import com.minbak.web.rooms.RoomsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CreateHostMapper {
    void insertRoom(HostDto hostDto);
    String getUserNameById(@Param("userId") int userId);
}
