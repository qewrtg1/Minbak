package com.minbak.web.dash_board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/dashboard")
public class DashBoardController {

    @Autowired
    DashBoardService dashBoardService;

    @GetMapping
    public String dashBoard(Model model){
        // 서비스에서 신고 건수 가져오기
        int userReportCount = dashBoardService.getUserReportCount();
        int roomReportCount = dashBoardService.getRoomReportCount();
        int hostVerificationCount = dashBoardService.getHostVerificationCount();

        // 모델에 데이터 추가
        model.addAttribute("userReportCount", userReportCount);
        model.addAttribute("roomReportCount", roomReportCount);
        model.addAttribute("hostVerificationCount", hostVerificationCount);

        Map<String, Integer> userRatio = dashBoardService.getUserRatio();

        if (userRatio == null || userRatio.isEmpty()) {
            userRatio.put("hostCount", 0);
            userRatio.put("guestCount", 0);
        }

        // 월별 예약 데이터 추가
        List<MonthlyReservationDto> monthlyReservations = dashBoardService.getMonthlyReservations();
        model.addAttribute("monthlyReservations", monthlyReservations);

        // 월별 결제 데이터 추가
        List<MonthlyRevenueDto> monthlyRevenue = dashBoardService.getMonthlyRevenue();
        model.addAttribute("monthlyRevenue", monthlyRevenue);

        model.addAttribute("userRatio", userRatio);

        // 통계 데이터 가져오기
        Map<String, Object> statistics = dashBoardService.getStatistics();

        // 모델에 데이터 추가
        model.addAttribute("statistics", statistics);

        // 최신 신고 숙소 데이터 가져오기
        List<ReportedRoomDto> recentReportedRooms = dashBoardService.getRecentReportedRooms();

        // 모델에 데이터 추가
        model.addAttribute("recentReportedRooms", recentReportedRooms);


        // 예약 취소 비율 데이터 가져오기
        Map<String, Integer> reservationRatio = dashBoardService.getReservationStatusRatio();

        // 모델에 데이터 추가
        model.addAttribute("reservationRatio", reservationRatio);

        // 관리자 리스트 가져오기
        List<AdminDto> adminList = dashBoardService.getAllAdmins();

        // 모델에 데이터 추가
        model.addAttribute("adminList", adminList);

        // 카테고리별 숙소 개수 데이터 추가
        List<CategoryRoomCountDto> categoryRoomCounts = dashBoardService.getRoomsByCategory();
        model.addAttribute("categoryRoomCounts", categoryRoomCounts);

        List<OptionRoomCountDto> optionRoomCounts = dashBoardService.getRoomsByOption();
        model.addAttribute("optionRoomCounts", optionRoomCounts);

        // Thymeleaf 템플릿 경로 반환
        return "dash-board";
    }

}
