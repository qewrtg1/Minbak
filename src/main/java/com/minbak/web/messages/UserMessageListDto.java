package com.minbak.web.messages;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserMessageListDto {
    private Integer userId;
    private Integer chatRoomId;
    private Integer lastMessageId;
    private LocalDateTime lastMessageTime;
    private String content;
    private int isRead;

}
