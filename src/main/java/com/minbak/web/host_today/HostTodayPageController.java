package com.minbak.web.host_today;

import com.minbak.web.spring_security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HostTodayPageController {

    private final HostTodayService hostTodayService;

    @GetMapping("/host/today")
    public String showHostTodayPage(Model model,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {

        int userId = userDetails.getUserId();
//        숙소 목록 보여주기
        List<HostTodayDto> rooms = hostTodayService.findMyRooms(userId);
        model.addAttribute("rooms", rooms);
//        등록된 숙소가 존재하는지 확인
        model.addAttribute("hasRooms", !rooms.isEmpty());
        // 체크아웃 예정 리스트 가져오기
        model.addAttribute("checkOutList", hostTodayService.getCheckOut(userId));
        return "host-pages/host-today-page"; // host-today-page.html을 렌더링
    }
}
