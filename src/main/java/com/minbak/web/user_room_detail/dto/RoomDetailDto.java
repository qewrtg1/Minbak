package com.minbak.web.user_room_detail.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RoomDetailDto {
    private DetailRoomResponse room;
    private DetailHostResponse host;
    private DetailBookResponse booking;
    private List<DetailReviewResponse> reviews;
}
