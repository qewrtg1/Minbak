package com.minbak.web.dash_board;

import org.apache.ibatis.annotations.Mapper;

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

}
