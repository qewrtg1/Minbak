package com.minbak.web.user_room_detail;

import com.minbak.web.user_room_detail.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomDetailMapper {
    DetailRoomResponse getRoomDetail(int roomId);
    DetailHostResponse getHostDetail(int roomId);
    DetailBookResponse getBookingDetail(int roomId);
    List<DetailReviewResponse> getRoomReviews(int roomId);
    List<String> getRoomImages(int roomId);
}
