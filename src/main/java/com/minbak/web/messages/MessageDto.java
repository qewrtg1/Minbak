package com.minbak.web.messages;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {

    private Integer messageId;
    private int senderId;
    private int receiverId;
    private String content;
    private LocalDateTime sentAt;
}
