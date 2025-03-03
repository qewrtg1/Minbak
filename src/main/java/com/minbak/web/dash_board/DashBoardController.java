package com.minbak.web.dash_board;

import com.minbak.web.books.BooksService;
import com.minbak.web.books.TopBooksOfRegionDto;
import com.minbak.web.books.TopDayOfWeekDto;
import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashBoardService dashBoardService;
    private final UsersService usersService;
    private final BooksService booksService;

    @GetMapping
    public String dashBoard(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 캐싱된 데이터 가져오기 (신고 건수, 통계, 예약 데이터)
        model.addAttribute("userReportCount", dashBoardService.getUserReportCount());
        model.addAttribute("roomReportCount", dashBoardService.getRoomReportCount());
        model.addAttribute("hostVerificationCount", dashBoardService.getHostVerificationCount());

        // 캐싱된 사용자 비율 가져오기
        Map<String, Integer> userRatio = dashBoardService.getUserRatio();
        model.addAttribute("userRatio", userRatio);

        // 캐싱된 월별 예약 데이터 추가
        model.addAttribute("monthlyReservations", dashBoardService.getMonthlyReservations());

        // 캐싱된 월별 매출 데이터 추가
        model.addAttribute("monthlyRevenue", dashBoardService.getMonthlyRevenue());

        // 캐싱된 통계 데이터 추가
        model.addAttribute("statistics", dashBoardService.getStatistics());

        // 캐싱된 최신 신고 숙소 데이터 추가
        model.addAttribute("recentReportedRooms", dashBoardService.getRecentReportedRooms());

        // 캐싱된 예약 취소 비율 데이터 추가
        model.addAttribute("reservationRatio", dashBoardService.getReservationStatusRatio());

        // 캐싱된 관리자 리스트 추가
        model.addAttribute("adminList", dashBoardService.getAllAdmins());

        // 캐싱된 카테고리별 숙소 개수 추가
        model.addAttribute("categoryRoomCounts", dashBoardService.getRoomsByCategory());

        // 캐싱된 옵션별 숙소 개수 추가
        model.addAttribute("optionRoomCounts", dashBoardService.getRoomsByOption());

        // 유저 정보 캐싱 적용
        model.addAttribute("user", usersService.findUserByUserId(userDetails.getUserId()));

        // 지역별 숙소 개수
        List<RegionRoomCountDto> regionRoomCounts = dashBoardService.countRoomsByRegion();
        model.addAttribute("regionRoomCounts", regionRoomCounts);

        // 인기 카테고리 (Top 5)
        List<PopularCategoryDto> popularCategories = dashBoardService.findPopularCategory();
        model.addAttribute("popularCategories", popularCategories);

        // 인기 옵션 (Top 5)
        List<PopularOptionDto> popularOptions = dashBoardService.findPopularOption();
        model.addAttribute("popularOptions", popularOptions);

        // 인기 많은 숙소 (Top 5)
        List<PopularRoomDto> popularRooms = dashBoardService.findPopularRooms();
        model.addAttribute("popularRooms", popularRooms);

        // 별점 높은 숙소 (Top 5)
        List<TopRatedRoomDto> topRatedRooms = dashBoardService.getTopRatedRooms();
        model.addAttribute("topRatedRooms", topRatedRooms);

        List<TopDayOfWeekDto> topDayOfWeek = booksService.findTopBooks();
        model.addAttribute("topDayOfWeek", topDayOfWeek);

        List<TopBooksOfRegionDto> topBooksOfRegion = booksService.findTopBooksOfRegion();
        model.addAttribute("topBooksOfRegion", topBooksOfRegion);

        return "dash-board";
    }
}
