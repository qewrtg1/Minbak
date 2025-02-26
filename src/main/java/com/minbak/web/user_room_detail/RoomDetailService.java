package com.minbak.web.user_room_detail;

import com.minbak.web.user_room_detail.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomDetailService {

    private final RoomDetailMapper roomDetailMapper;

    public RoomDetailDto getRoomDetail(int roomId) {
        DetailRoomResponse room = roomDetailMapper.getRoomDetail(roomId);
        DetailHostResponse host = roomDetailMapper.getHostDetail(roomId);
        DetailBookResponse booking = roomDetailMapper.getBookingDetail(roomId);
        List<DetailReviewResponse> reviews = roomDetailMapper.getRoomReviews(roomId);

        if (room != null) {
            // 해당 room_id의 이미지 리스트를 가져와서 삽입
            List<String> imageUrls = roomDetailMapper.getRoomImages(roomId);
            room.setImageUrls(imageUrls);
        }

        RoomDetailDto roomDetailDto = new RoomDetailDto();
        roomDetailDto.setRoom(room);
        roomDetailDto.setHost(host);
        roomDetailDto.setBooking(booking);
        roomDetailDto.setReviews(reviews);

        return roomDetailDto;
    }
}
