package com.minbak.web.messages;
import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserMessageApiController {

    @Autowired MessageService messageService;

    @PostMapping("/user/messageList/create")
    public ResponseEntity<Map<String, Object>> createMessageInChatRoom(@RequestBody RequestUserMessageDto requestUserMessageDto,

                                        @AuthenticationPrincipal CustomUserDetails userDetails){

        MessageDto messageDto=new MessageDto();


        messageDto.setContent(requestUserMessageDto.getContent());
        messageDto.setSenderId(userDetails.getUserId());
        messageDto.setReceiverId(requestUserMessageDto.getReceiverId());

        messageService.userCreateMessage(messageDto);

        // JSON 응답 생성
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Message created successfully");

        return ResponseEntity.ok(response);
    }

}


