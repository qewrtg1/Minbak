package com.minbak.web.messages;


import com.minbak.web.users.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
//    유저 목록 조회
    List<UserDto> findAllUsers();

//    유저 아이디에 따른 메세지 조회
    List<MessageDto> findMessagesById(int user_id);
}
