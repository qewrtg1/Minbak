package com.minbak.web.messages;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    // 유저조회
    public List<UserDto> findAllUsers(){
        return MessageMapper.findAllUsers();
    }
}
