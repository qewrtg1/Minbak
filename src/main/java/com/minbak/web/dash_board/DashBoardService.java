package com.minbak.web.dash_board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashBoardService {

    @Autowired
    DashBoardMapper dashBoardMapper;

    public int getUserReportCount() {
        return dashBoardMapper.countUserReports();
    }

    public int getRoomReportCount() {
        return dashBoardMapper.countRoomReports();
    }

    public int getHostVerificationCount() {
        return dashBoardMapper.countHostVerifications();
    }

    public Map<String, Integer> getUserRatio() {
        int totalUsers = dashBoardMapper.countTotalUsers();
        int hostCount = dashBoardMapper.countHosts();
        int guestCount = totalUsers - hostCount;

        Map<String, Integer> userRatio = new HashMap<>();
        userRatio.put("hostCount", hostCount);
        userRatio.put("guestCount", guestCount);

        return userRatio;
    }

    public List<MonthlyReservationDto> getMonthlyReservations() {
        // 현재 월 가져오기
        YearMonth currentMonth = YearMonth.now();
        YearMonth nextMonth = currentMonth.plusMonths(1); // 다음 달 추가

        List<String> lastFiveMonths = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            lastFiveMonths.add(currentMonth.minusMonths(i).toString()); // "YYYY-MM" 형태
        }
        lastFiveMonths.add(nextMonth.toString()); // 다음 달 추가

        List<MonthlyReservationDto> reservations = dashBoardMapper.findMonthlyReservations();

        return reservations.stream()
                .filter(r -> lastFiveMonths.contains(r.getMonth())) // 올바른 월 필터링
                .collect(Collectors.toList());
    }

    public List<MonthlyRevenueDto> getMonthlyRevenue() {
        YearMonth currentMonth = YearMonth.now();

        List<String> lastFiveMonths = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            lastFiveMonths.add(currentMonth.minusMonths(i).toString()); // "YYYY-MM" 형태
        }

        List<MonthlyRevenueDto> revenues = dashBoardMapper.findMonthlyRevenue();

        return revenues.stream()
                .filter(r -> lastFiveMonths.contains(r.getMonth()))
                .collect(Collectors.toList());
    }


}
