package com.minbak.web.messages;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseMessageDto {
    private int messageId;
    private int senderId;
    private int receiverId;
    private String content;
    private LocalDateTime sentAt;

    private String senderName;
    private String receiverName;
    private String senderEmail;
    private String receiverEmail;
    private String senderPhoneNumber;
    private String receiverPhoneNumber;

}
