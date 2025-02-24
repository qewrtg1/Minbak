package com.minbak.web.dash_board;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface DashBoardMapper {

    int countUserReports();
    int countRoomReports();
    int countHostVerifications();
    int countTotalUsers(); // 전체 유저 수 조회
    int countHosts(); // 호스트 수 조회
    List<MonthlyReservationDto> findMonthlyReservations();
    List<MonthlyRevenueDto> findMonthlyRevenue(); // 추가
    int countTotalRooms(); // 전체 숙소 수
    int countTodayUsers(); // 오늘 가입자 수
    BigDecimal sumTotalRevenue(); // 총 매출 합산
    int countPendingReports(); // 미처리 신고 건수
    BigDecimal avgReviewScore(); // 리뷰 평균 점수
    List<ReportedRoomDto> findRecentReportedRooms(); // 최신 신고 숙소 5개 조회
    int countTotalReservations(); // 전체 예약 수 조회
    int countCancelledReservations(); // 취소된 예약 수 조회
    List<AdminDto> findAllAdmins();
    List<CategoryRoomCountDto> countRoomsByCategory();
    List<OptionRoomCountDto> countRoomsByOption();
}
