package com.minbak.web.messages;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//리퀘스트 맵핑 부분 admin 인지 보드인지...
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

//    유저조회
    @GetMapping("/admin/userMessages")
    public String userMessages(Model model){

        //유저 정보를 받아서 UserDto객체에 넣고 MessageDto 전달.

        //카테고리 목록 받아서 model로 전달
        List<UserDto> users= messageService.findAllUsers();
        model.addAttribute("users",users);
        return "/message/userMessage";
    }

//    특정 유저 메세지 확인
    @GetMapping("/admin/userMessages/{user_id}")
    public String userMessagesById(@PathVariable("user_id") int user_id){
        messageService.

    }

}
