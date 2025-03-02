package com.minbak.web.dash_board;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashBoardService {

    private final DashBoardMapper dashBoardMapper;

    public DashBoardService(DashBoardMapper dashBoardMapper) {
        this.dashBoardMapper = dashBoardMapper;
    }

    // 신고 건수 캐싱
    @Cacheable(value = "dashboard", key = "'userReportCount'")
    public int getUserReportCount() {
        return dashBoardMapper.countUserReports();
    }

    @Cacheable(value = "dashboard", key = "'roomReportCount'")
    public int getRoomReportCount() {
        return dashBoardMapper.countRoomReports();
    }

    @Cacheable(value = "dashboard", key = "'hostVerificationCount'")
    public int getHostVerificationCount() {
        return dashBoardMapper.countHostVerifications();
    }

    // 통계 정보 캐싱
    @Cacheable(value = "dashboard", key = "'statistics'")
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", dashBoardMapper.countTotalUsers());
        stats.put("totalRooms", dashBoardMapper.countTotalRooms());
        stats.put("todayUsers", dashBoardMapper.countTodayUsers());

        // NULL 방지 기본값 설정
        BigDecimal totalRevenue = dashBoardMapper.sumTotalRevenue();
        stats.put("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);

        BigDecimal avgReviewScore = dashBoardMapper.avgReviewScore();
        stats.put("avgReviewScore", avgReviewScore != null ? avgReviewScore : BigDecimal.ZERO);

        stats.put("pendingReports", dashBoardMapper.countPendingReports());
        return stats;
    }

    // 유저 비율 캐싱 (호스트 vs 게스트)
    @Cacheable(value = "dashboard", key = "'userRatio'")
    public Map<String, Integer> getUserRatio() {
        int totalUsers = dashBoardMapper.countTotalUsers();
        int hostCount = dashBoardMapper.countHosts();
        int guestCount = totalUsers - hostCount;

        Map<String, Integer> userRatio = new HashMap<>();
        userRatio.put("hostCount", hostCount);
        userRatio.put("guestCount", guestCount);
        return userRatio;
    }

    // 월별 예약 데이터 캐싱
    @Cacheable(value = "dashboard", key = "'monthlyReservations'")
    public List<MonthlyReservationDto> getMonthlyReservations() {
        return dashBoardMapper.findMonthlyReservations();
    }

    // 월별 매출 데이터 캐싱
    @Cacheable(value = "dashboard", key = "'monthlyRevenue'")
    public List<MonthlyRevenueDto> getMonthlyRevenue() {
        return dashBoardMapper.findMonthlyRevenue();
    }

    // 최신 신고 숙소 데이터 캐싱
    @Cacheable(value = "dashboard", key = "'recentReportedRooms'")
    public List<ReportedRoomDto> getRecentReportedRooms() {
        return dashBoardMapper.findRecentReportedRooms();
    }

    // 예약 취소 비율 캐싱
    @Cacheable(value = "dashboard", key = "'reservationRatio'")
    public Map<String, Integer> getReservationStatusRatio() {
        int totalReservations = dashBoardMapper.countTotalReservations();
        int cancelledReservations = dashBoardMapper.countCancelledReservations();
        int completedReservations = totalReservations - cancelledReservations;

        Map<String, Integer> reservationRatio = new HashMap<>();
        reservationRatio.put("cancelled", cancelledReservations);
        reservationRatio.put("completed", completedReservations);
        return reservationRatio;
    }

    // 관리자 리스트 캐싱
    @Cacheable(value = "dashboard", key = "'adminList'")
    public List<AdminDto> getAllAdmins() {
        return dashBoardMapper.findAllAdmins();
    }

    // 카테고리별 숙소 개수 캐싱
    @Cacheable(value = "dashboard", key = "'categoryRoomCounts'")
    public List<CategoryRoomCountDto> getRoomsByCategory() {
        return dashBoardMapper.countRoomsByCategory();
    }

    // 옵션별 숙소 개수 캐싱
    @Cacheable(value = "dashboard", key = "'optionRoomCounts'")
    public List<OptionRoomCountDto> getRoomsByOption() {
        return dashBoardMapper.countRoomsByOption();
    }

    public void sendMessage(Integer senderId, Integer receiverId, String content) {
        dashBoardMapper.insertMessage(senderId, receiverId, content);
    }
}

