package com.minbak.web.dash_board;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface DashBoardMapper {

    int countUserReports();
    int countRoomReports();
    int countHostVerifications();
    int countTotalUsers();
    int countHosts();
    List<MonthlyReservationDto> findMonthlyReservations();
    List<MonthlyRevenueDto> findMonthlyRevenue();
    int countTotalRooms();
    int countTodayUsers();
    BigDecimal sumTotalRevenue();
    int countPendingReports();
    BigDecimal avgReviewScore();
    List<ReportedRoomDto> findRecentReportedRooms();
    int countTotalReservations();
    int countCancelledReservations();
    List<AdminDto> findAllAdmins();
    List<CategoryRoomCountDto> countRoomsByCategory();
    List<OptionRoomCountDto> countRoomsByOption();
    void insertMessage(Integer senderId, Integer receiverId, String content);

    List<RegionRoomCountDto> countRoomsByRegion();

    List<PopularCategoryDto> findPopularCategory();

    List<PopularOptionDto> findPopularOption();

    List<PopularRoomDto> findPopularRooms();

    List<TopRatedRoomDto> findTopRatedRooms();

}


