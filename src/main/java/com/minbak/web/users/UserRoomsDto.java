package com.minbak.web.users;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoomsDto {
    private int roomId;              // 숙소 고유 ID
    private String name;
    private String content;      // 숙소 설명
    private String address;      // 숙소 주소
    private double price;
    private String useGuide;
    private double latitude;
    private double longitude;
    private Integer userId;
    private String userEmail;

}
