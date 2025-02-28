package com.minbak.web.host_today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostTodayService {

    private final HostTodayMapper hostTodayMapper;

    public List<HostTodayDto> getCheckOut(int hostId) {
        return hostTodayMapper.getCheckOut(hostId);
    }

    public List<HostTodayDto> getOngoing(int hostId) {
        return hostTodayMapper.getOngoing(hostId);
    }

    public List<HostTodayDto> getCheckIn(int hostId) {
        return hostTodayMapper.getCheckIn(hostId);
    }

    public List<HostTodayDto> getUpcoming(int hostId) {
        return hostTodayMapper.getUpcoming(hostId);
    }

    public List<HostTodayDto> getPendingReviews(int hostId) {
        return hostTodayMapper.getPendingReviews(hostId);
    }
}
