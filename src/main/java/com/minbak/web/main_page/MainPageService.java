package com.minbak.web.main_page;

import com.minbak.web.main_page.dto.MainPageResponseDto;
import com.minbak.web.main_page.dto.MainRoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MainPageService {

    @Autowired
    private MainPageMapper mainPageMapper;

    public MainPageResponseDto getMainPageData(int userId) {
        MainPageResponseDto response = new MainPageResponseDto();

        // 숙소 정보 가져오기
        List<MainRoomDto> rooms = mainPageMapper.findRecentRooms();
        for (MainRoomDto room : rooms) {
            room.setImages(mainPageMapper.findRoomImages(room.getRoomId()));
            room.setRecentReview(mainPageMapper.findRecentReviewByRoom(room.getRoomId()));
        }
        response.setRooms(rooms);

        // 카테고리 정보
        response.setCategories(mainPageMapper.findCategories());

        // 옵션 정보
        response.setOptions(mainPageMapper.findOptions());

        // 로그인한 사용자 정보
        if (userId > 0) {
            response.setUser(mainPageMapper.findUserById(userId));
        }

        return response;
    }
}
