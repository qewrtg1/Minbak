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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
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
// 채팅방 리스트
        //리시버아이디가 내 거인 메시지만 is_read 카운트
        List<UserMessageListDto> userMessageLists=messageService.showUserMessageList(userDetails.getUserId());
        for (UserMessageListDto userMessageList : userMessageLists){
            userMessageList.setIsUnRead(messageService.countMessagesByIds(userDetails.getUserId(),userMessageList.getChatRoomId()));
        }
        // DetailUserResponse 리스트를 생성
        List<DetailUserResponse> detailUserResponseList = new ArrayList<>();

// userMessageLists 리스트를 순회하면서 DetailUserResponse 객체를 추가
        for (UserMessageListDto userMessageList : userMessageLists) {
            // 사용자 정보 조회
            DetailUserResponse detailUserResponse = usersService.getUserInfo(userMessageList.getChatRoomId());

            // 리스트에 DetailUserResponse 객체를 추가
            detailUserResponseList.add(detailUserResponse);
        }
        Collections.sort(userMessageLists, (m1, m2) -> m2.getLastMessageTime().compareTo(m1.getLastMessageTime()));
        model.addAttribute("userMessageLists",userMessageLists);
        model.addAttribute("detailUserResponseLists",detailUserResponseList);


        return "user-pages/user-message";
    }
//    메세지 상세보기
    @GetMapping("/messageList/{chatRoomId}")
    public String showUserMessageDetail(@PathVariable("chatRoomId") int chatRoomId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails,
                                        Model model){
//채팅방 리스트
        List<UserMessageListDto> userMessageLists=messageService.showUserMessageList(userDetails.getUserId());
//        읽음 처리
//        if(){ 리시버 아이디가 유저 아이디가 맞다면
            messageService.checkIsRead(userDetails.getUserId(),chatRoomId);
//        }
//        읽음 안읽음 체크
        for (UserMessageListDto userMessageList:userMessageLists){
            userMessageList.setIsUnRead(messageService.countMessagesByIds(userDetails.getUserId(),userMessageList.getChatRoomId()));
        }
        // DetailUserResponse 리스트를 생성
        List<DetailUserResponse> detailUserResponseList = new ArrayList<>();

// userMessageLists 리스트를 순회하면서 DetailUserResponse 객체를 추가
        for (UserMessageListDto userMessageList : userMessageLists) {
            // 사용자 정보 조회
            DetailUserResponse detailUserResponse = usersService.getUserInfo(userMessageList.getChatRoomId());

            // 리스트에 DetailUserResponse 객체를 추가
            detailUserResponseList.add(detailUserResponse);
        }
//        정렬
        Collections.sort(userMessageLists, (m1, m2) -> m2.getLastMessageTime().compareTo(m1.getLastMessageTime()));
        model.addAttribute("userMessageLists",userMessageLists);
        model.addAttribute("detailUserResponseLists",detailUserResponseList);

//채팅 메세지 내용
        Integer userId =userDetails.getUserId();
        List<MessageDto> userMessagesDetail=messageService.showUserMessageDetail(userId,chatRoomId);
        String chatRoomName=usersService.findUserByUserId(chatRoomId).getName();
        Collections.sort(userMessagesDetail, (m1, m2) -> m1.getSentAt().compareTo(m2.getSentAt()));
        model.addAttribute("userMessages",userMessagesDetail);
        model.addAttribute("userId",userDetails.getUserId());
        model.addAttribute("chatRoomName",chatRoomName);
        model.addAttribute("chatRoomId",chatRoomId);
        return "user-pages/user-message";
    }
    //    유저 읽음,안읽음 체크 기능
    @PostMapping("/messageList")
    public void checkUserMessage(@RequestParam int chatRoomId, @AuthenticationPrincipal CustomUserDetails userDetails){

        messageService.checkIsRead(userDetails.getUserId(),chatRoomId);

    }
    // 메세지 보내기(유저)
//    @PostMapping("/messageList/create")
//    public void createMessageInChatRoom(@RequestBody String content,
//                                        @RequestBody String chatRoomId,
//                              @ModelAttribute MessageDto messageDto,
//                              @AuthenticationPrincipal CustomUserDetails userDetails){
//
//        messageDto.setContent(content);
//        messageDto.setSenderId(userDetails.getUserId());
//        messageDto.setReceiverId(Integer.valueOf(chatRoomId));
//
//        messageService.userCreateMessage(messageDto);
//
//
//    }

}
