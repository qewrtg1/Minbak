package com.minbak.web.host_room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HostRoomDTO {
    private int roomId;
    private String name;
    private String address;
    private int price;
    private String content;
    private int hostId;
    private String title; // 추가
    private int userId;
}