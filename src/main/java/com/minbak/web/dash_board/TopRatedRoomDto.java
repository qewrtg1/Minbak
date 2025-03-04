package com.minbak.web.dash_board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopRatedRoomDto {
    private int roomId;
    private String roomName;
    private String address;
    private double avgRating;
    private int reviewCount;
}
