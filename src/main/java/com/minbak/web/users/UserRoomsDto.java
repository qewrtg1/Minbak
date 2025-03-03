package com.minbak.web.users;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoomsDto {
    private int roomId;
    private String name;
    private String content;
    private String title;
    private String address;
    private double price;
    private String useGuide;
    private double latitude;
    private double longitude;
    private Integer userId;
    private String userEmail;
    private Integer maxGuests;
    private Integer bedrooms;
    private Integer beds;
    private Integer bathrooms;
    private String buildingType;
}
