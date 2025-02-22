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

        // Thymeleaf 템플릿 경로 반환
        return "dash-board";
    }

}
