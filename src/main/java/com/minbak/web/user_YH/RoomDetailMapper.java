package com.minbak.web.user_YH;

import com.minbak.web.user_YH.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface RoomDetailMapper {
    DetailRoomResponse getRoomDetail(int roomId);
    DetailHostResponse getHostDetail(int roomId);
    DetailBookResponse getBookingDetail(int roomId);
    List<DetailReviewResponse> getRoomReviews(int roomId);
    List<String> getRoomImages(int roomId);
    List<Map<String, LocalDate>> getReservedDates(int roomId);
    String getHostProfileImage(Integer hostId);
    List<Map<String, String>> getRoomOptions(Integer roomId);
    String findImageUrlsByUserId(int userId);
}
