package com.minbak.web.main_page;

import com.minbak.web.main_page.dto.MainPageResponseDto;
import com.minbak.web.main_page.dto.MainRoomDto;
import com.minbak.web.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/main")
public class MainPageApiController {

    @Autowired
    private MainPageService mainPageService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public MainPageResponseDto getMainPageData() {

        return mainPageService.getMainPageData();

    }

    @GetMapping("/search")
    public List<MainRoomDto> searchRooms(
            @RequestParam(required = false) String destination) {

        return mainPageService.getSearchPageData(destination);
    }
}
