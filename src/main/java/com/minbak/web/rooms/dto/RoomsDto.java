package com.minbak.web.rooms.dto;

import lombok.Data;




@Data
public class RoomsDto {
    private Integer roomId;
    private String name;
    private String content;
    private String address;
    private double price;
    private String useGuide;
    private double latitude;
    private double longitude;


}
