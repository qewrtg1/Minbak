package com.minbak.web.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserMessageDto {
    private String content;
    private Integer receiverId;
}
