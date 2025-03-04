package com.minbak.web.messages;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserMessageListDto {
    private Integer userId;
    private Integer chatRoomId;
    private String chatRoomUserName;
    private Integer lastMessageId;
    private LocalDateTime lastMessageTime;
    private String lastMessageContent;
    private Integer isRead;

}
