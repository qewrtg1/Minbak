package com.minbak.web.host_today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/host/today")
@RequiredArgsConstructor
public class HostTodayController {

    private final HostTodayService hostTodayService;

    @GetMapping("/checkout")
    public List<HostTodayDto> getCheckOut(@RequestParam int hostId) {
        return hostTodayService.getCheckOut(hostId);
    }

    @GetMapping("/ongoing")
    public List<HostTodayDto> getOngoing(@RequestParam int hostId) {
        return hostTodayService.getOngoing(hostId);
    }

    @GetMapping("/checkin")
    public List<HostTodayDto> getCheckIn(@RequestParam int hostId) {
        return hostTodayService.getCheckIn(hostId);
    }

    @GetMapping("/upcoming")
    public List<HostTodayDto> getUpcoming(@RequestParam int hostId) {
        return hostTodayService.getUpcoming(hostId);
    }

    @GetMapping("/pending-review")
    public List<HostTodayDto> getPendingReviews(@RequestParam int hostId) {
        return hostTodayService.getPendingReviews(hostId);
    }
}
