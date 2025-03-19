package com.minbak.web.main_page;

import com.minbak.web.main_page.dto.MainImageFileDto;
import com.minbak.web.main_page.dto.MainOptionDto;
import com.minbak.web.main_page.dto.MainReviewDto;
import com.minbak.web.main_page.dto.MainRoomDto;
import com.minbak.web.review.ReviewDto;
import com.minbak.web.rooms.dto.CategoryDto;
import com.minbak.web.users.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MainPageMapper {

    // 최신 숙소 20개 가져오기
    List<MainRoomDto> findRecentRooms();

    //서치한 숙소 가져오기
    List<MainRoomDto> searchRoomsByDestination(String destination);

    // 특정 숙소의 이미지 가져오기
    MainImageFileDto findRoomImages(int roomId);

    // 카테고리 목록 가져오기 (order 순)
    List<CategoryDto> findCategories();

    // 모든 옵션 정보 가져오기
    List<MainOptionDto> findOptions();

    // 특정 숙소의 가장 최근 리뷰 가져오기
    MainReviewDto findRecentReviewByRoom(int roomId);

    // 로그인한 사용자 정보 가져오기
    UserDto findUserById(@Param("userId") int userId);
}
