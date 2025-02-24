package com.minbak.web.dash_board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    //통계 데이터 처리
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", dashBoardMapper.countTotalUsers()); // 전체 사용자 수
        stats.put("totalRooms", dashBoardMapper.countTotalRooms()); // 전체 숙소 수
        stats.put("todayUsers", dashBoardMapper.countTodayUsers()); // 오늘 가입자 수

        // 매출 데이터가 NULL일 경우 0으로 처리
        BigDecimal totalRevenue = dashBoardMapper.sumTotalRevenue();
        stats.put("totalRevenue", (totalRevenue != null) ? totalRevenue : BigDecimal.ZERO);

        stats.put("pendingReports", dashBoardMapper.countPendingReports()); // 미처리 신고 건수

        // 리뷰 평균이 NULL일 경우 0.0 처리
        BigDecimal avgReviewScore = dashBoardMapper.avgReviewScore();
        stats.put("avgReviewScore", (avgReviewScore != null) ? avgReviewScore : BigDecimal.ZERO);

        return stats;
    }

    public List<ReportedRoomDto> getRecentReportedRooms() {
        return dashBoardMapper.findRecentReportedRooms();
    }

    //예약 취소 비율 코드
    public Map<String, Integer> getReservationStatusRatio() {
        int totalReservations = dashBoardMapper.countTotalReservations(); // 전체 예약 수
        int cancelledReservations = dashBoardMapper.countCancelledReservations(); // 취소된 예약 수
        int completedReservations = totalReservations - cancelledReservations; // 정상 완료된 예약

        Map<String, Integer> reservationRatio = new HashMap<>();
        reservationRatio.put("cancelled", cancelledReservations);
        reservationRatio.put("completed", completedReservations);

        return reservationRatio;
    }


    public List<AdminDto> getAllAdmins() {
        return dashBoardMapper.findAllAdmins();
    }

    public List<CategoryRoomCountDto> getRoomsByCategory() {
        return dashBoardMapper.countRoomsByCategory();
    }

    public List<OptionRoomCountDto> getRoomsByOption() {
        return dashBoardMapper.countRoomsByOption();
    }
}
