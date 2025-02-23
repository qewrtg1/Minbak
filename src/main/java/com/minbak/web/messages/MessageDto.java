package com.minbak.web.messages;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {

    private Integer messageId;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private LocalDateTime sentAt;
}
