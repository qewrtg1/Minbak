package com.minbak.web.user_YH;

import com.minbak.web.user_YH.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomDetailService {

    private final RoomDetailMapper roomDetailMapper;

    public RoomDetailDto getRoomDetail(int roomId) {
        DetailRoomResponse room = roomDetailMapper.getRoomDetail(roomId);
        DetailHostResponse host = roomDetailMapper.getHostDetail(roomId);
        if(host.getImageUrl() == null){

        }
        DetailBookResponse booking = roomDetailMapper.getBookingDetail(roomId);
        List<DetailReviewResponse> reviews = roomDetailMapper.getRoomReviews(roomId);
        host.setImageUrl(roomDetailMapper.getHostProfileImage(host.getHostId()));
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

    public List<Map<String, LocalDate>> getReservedDates(int roomId) {
        return roomDetailMapper.getReservedDates(roomId);
    }

    public Map<String, List<String>> getRoomOptions(Integer roomId) {
        List<Map<String, String>> rawOptions = roomDetailMapper.getRoomOptions(roomId);

        // 카테고리별 그룹화
        return rawOptions.stream()
                .collect(Collectors.groupingBy(
                        map -> map.get("category"),
                        Collectors.mapping(map -> map.get("option_name"), Collectors.toList())
                ));
    }

    public String findImageUrlsByUserId(int userId){
        return roomDetailMapper.findImageUrlsByUserId(userId);
    }
}
