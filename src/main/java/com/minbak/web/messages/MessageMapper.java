package com.minbak.web.messages;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
//    유저 목록 조회
    List<UserDto> findAllUsers();
}
