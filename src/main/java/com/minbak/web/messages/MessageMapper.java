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

//    오늘자 메세지 개수 조회
    int countMessagesToday();

    //    전체 메세지 개수 조회
    int countAllMessages();

//    메세지 id순 내림차순 정렬, 페이지네이션 적용
    List<MessageDto> findMessagesByLimitAndOffset(int limit, int offset);
}
