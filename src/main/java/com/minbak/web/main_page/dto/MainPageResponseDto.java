package com.minbak.web.main_page.dto;

import com.minbak.web.rooms.dto.CategoryDto;
import com.minbak.web.users.UserDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MainPageResponseDto {
    private List<MainRoomDto> rooms; // 숙소 목록
    private List<CategoryDto> categories; // 카테고리 목록
    private List<MainOptionDto> options; // 옵션 목록
    private UserDto user; // 로그인한 사용자 정보
}