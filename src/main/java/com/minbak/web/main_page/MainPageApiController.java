package com.minbak.web.main_page;

import com.minbak.web.main_page.dto.MainPageResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainPageApiController {

    @Autowired
    private MainPageService mainPageService;

    @GetMapping
    public MainPageResponseDto getMainPageData(@AuthenticationPrincipal UserDetails userDetails) {
        int userId = (userDetails != null) ? Integer.parseInt(userDetails.getUsername()) : 0;
        return mainPageService.getMainPageData(userId);
    }
}
