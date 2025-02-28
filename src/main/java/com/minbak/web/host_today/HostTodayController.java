package com.minbak.web.host_today;

import com.minbak.web.spring_security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/host/today")
@RequiredArgsConstructor
public class HostTodayController {

    private final HostTodayService hostTodayService;

    @GetMapping("/checkout")
    public List<HostTodayDto> getCheckOut(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUserId();
        return hostTodayService.getCheckOut(userId);
    }

    @GetMapping("/ongoing")
    public List<HostTodayDto> getOngoing(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUserId();
        return hostTodayService.getOngoing(userId);
    }

    @GetMapping("/checkin")
    public List<HostTodayDto> getCheckIn(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUserId();
        return hostTodayService.getCheckIn(userId);
    }

    @GetMapping("/upcoming")
    public List<HostTodayDto> getUpcoming(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUserId();
        return hostTodayService.getUpcoming(userId);
    }

    @GetMapping("/pending-review")
    public List<HostTodayDto> getPendingReviews(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUserId();
        return hostTodayService.getPendingReviews(userId);
    }
}
