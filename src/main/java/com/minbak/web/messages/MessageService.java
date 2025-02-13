package com.minbak.web.messages;


import com.minbak.web.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;
    // 유저조회
    public List<UserDto> findAllUsers(){
        return messageMapper.findAllUsers();
    }

    // 유저 아이디에 따른 메세지 내용 조회
    public List<MessageDto> findMessagesById(int user_id) {
        return  messageMapper.findMessagesById(user_id);

    }
}
