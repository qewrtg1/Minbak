package com.minbak.web.host_today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostTodayService {

    private final HostTodayMapper hostTodayMapper;

    public List<HostTodayDto> getCheckOut(int userId) {
        return hostTodayMapper.getCheckOut(userId);
    }

    public List<HostTodayDto> getOngoing(int userId) {
        return hostTodayMapper.getOngoing(userId);
    }

    public List<HostTodayDto> getCheckIn(int userId) {
        return hostTodayMapper.getCheckIn(userId);
    }

    public List<HostTodayDto> getUpcoming(int userId) {
        return hostTodayMapper.getUpcoming(userId);
    }

    public List<HostTodayDto> getPendingReviews(int userId) {
        return hostTodayMapper.getPendingReviews(userId);
    }
}
