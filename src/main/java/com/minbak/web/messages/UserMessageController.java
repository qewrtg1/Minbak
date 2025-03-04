package com.minbak.web.messages;

import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.user_YH.dto.DetailUserResponse;
import com.minbak.web.users.UserDto;
import com.minbak.web.users.UsersMapper;
import com.minbak.web.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserMessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    UsersService usersService;

    @GetMapping("/messageList")
    public String showUserMessageList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model){

        List<UserMessageListDto> userMessageLists=messageService.showUserMessageList(userDetails.getUserId());
        // DetailUserResponse 리스트를 생성
        List<DetailUserResponse> detailUserResponseList = new ArrayList<>();

// userMessageLists 리스트를 순회하면서 DetailUserResponse 객체를 추가
        for (UserMessageListDto userMessageList : userMessageLists) {
            // 사용자 정보 조회
            DetailUserResponse detailUserResponse = usersService.getUserInfo(userMessageList.getChatRoomId());

            // 리스트에 DetailUserResponse 객체를 추가
            detailUserResponseList.add(detailUserResponse);
        }
        model.addAttribute("userMessageLists",userMessageLists);
        model.addAttribute("detailUserResponseLists",detailUserResponseList);


        return "/user-pages/user-message";
    }
//    메세지 상세보기
    @GetMapping("/messageList/{chatRoomId}")
    public String showUserMessageDetail(@PathVariable("chatRoomId") int chatRoomId, @AuthenticationPrincipal CustomUserDetails userDetails){

        messageService.showUserMessageDetail(userDetails.getUserId(),chatRoomId);
        return "/user-pages/user-message";
    }
    //    유저 읽음,안읽음 체크 기능
    @PostMapping("/messageList")
    public void checkUserMessage(@RequestParam int chatRoomId, @AuthenticationPrincipal CustomUserDetails userDetails){

        messageService.checkIsRead(userDetails.getUserId(),chatRoomId);

    }


}
