package com.minbak.web.main_page;

import com.minbak.web.main_page.dto.MainPageResponseDto;
import com.minbak.web.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainPageApiController {

    @Autowired
    private MainPageService mainPageService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public MainPageResponseDto getMainPageData() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null){
            int userId = usersService.findUserIdByEmail(authentication.getName());
            return mainPageService.getMainPageData(userId);

        }else {
            return mainPageService.getMainPageData(0);
        }

    }
}
