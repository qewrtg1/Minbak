package com.minbak.web.dash_board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportedRoomDto {
    private int userId;      // 유저 ID 추가
    private String userEmail; // 유저 이메일 추가
    private int roomId;
    private String roomName;
    private String address;
    private int price;
}