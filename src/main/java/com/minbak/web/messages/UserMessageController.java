package com.minbak.web.messages;

import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.users.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserMessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    UsersMapper usersMapper;

@RequestMapping("/user")
    @GetMapping("/messageList")
    public String showUserMessageList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model){

        List<UserMessageListDto> userMessageLists=messageService.showUserMessageList(userDetails.getUserId());
        model.addAttribute("userMessageLists",userMessageLists);


        return "/message/messageList";
    }
//    메세지 상세보기
    @GetMapping("/messageList/chatId")
    public String showUserMessageDetail(@RequestParam int chatRoomId, @AuthenticationPrincipal CustomUserDetails userDetails){

        messageService.checkIsRead(userDetails.getUserId(),chatRoomId);
        return "/message/messageList";
    }
    //    유저 읽음,안읽음 체크 기능
    @PostMapping("/messageList")
    public void checkUserMessage(@RequestParam int chatRoomId, @AuthenticationPrincipal CustomUserDetails userDetails){

        messageService.checkIsRead(userDetails.getUserId(),chatRoomId);

    }


}
