package com.minbak.web.host_room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HostRoomDTO {
    private int roomId;
    private int userId;
    private String name;
    private String address;
    private int price;
    private String content;
    private String title;
    private int maxGuests;
    private String buildingType;



//    private Integer roomId;
//    private Integer userId; // 호스트 ID
//    private String title;
//    private String content;
//    private String address;
//    private int price;
//
}