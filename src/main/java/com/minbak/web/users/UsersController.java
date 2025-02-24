package com.minbak.web.users;

import com.minbak.web.common.dto.PageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/login")
    public String loginPage() {
        return "/spring-security/login";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "/spring-security/admin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "/spring-security/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            return "redirect:/signup";
        }
        // 유효성 검사 성공 시 처리
        usersService.createUser(userDto);

        return "redirect:/login";
    }


//    @GetMapping("/my/info")
//    public String myPage(Model model) {
//
//        //세션의 현재 사용자 id, 현 프로젝트에서는 email
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        //세션의 현재 사용자의 role ROLE_USER or ROLE_ADMIN
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//        //하나의 role만 가져오는 코드. 이코드가 아니어도 id값으로 원하는 정보를 가져오는 로직을 service에서 짜면 됨.
//
//
//        model.addAttribute("id", email);
//        model.addAttribute("role", role);
//
//        return "/spring-security/mypage";
//    }
//
//
//    //get메서드 로그아웃 코드
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            new SecurityContextLogoutHandler().logout(request, response, authentication);
//        }
//
//        return "redirect:/";
//    }


//    @GetMapping("/users/sample")
//    //어드민 메인페이지 페이지네이션
//    public String adminMainPageSample(@RequestParam(name ="page", defaultValue = "1")int page,
//                                @RequestParam(name ="size", defaultValue = "10")int size,
//                                Model model){
//
//        //페이지에 보여줄 유저 정보 가져오기
//        UserPageDto<UserResponseDto> userPageDto = usersService.findUsersByLimitAndOffset(page, size);
//
//        //호스트 숫자 가져오기
//        int allHostNum = usersService.countUserRolesByRoleId(2);
//
//        //오늘 가입한 가입자 수 가져오기
//        int UsersJoinedTodayNum = usersService.countUsersJoinedToday();
//
//        //모든 관리자 수 가져오기
//        int allAdminNum = usersService.countUserRolesByRoleId(3);
//
//        //이번 주 요일별 가입자 수
//        // Map<Integer, Integer> 형태로 결과를 가져옴 0 = 일요일 6 = 토요일
//        List<Map<Integer, Integer>> weekdayUserCounts = usersService.countUsersJoinedByWeekday();
//
//        model.addAttribute("usersByWeekday", weekdayUserCounts);
//
//        model.addAttribute("allAdminNum", allAdminNum);
//        model.addAttribute("UsersJoinedTodayNum", UsersJoinedTodayNum);
//        model.addAttribute("allHostNum", allHostNum);
//        model.addAttribute("userPageDto",userPageDto);
//        // 모델에 데이터 전달
//
//        return "/users/user-main";
//    }

    @GetMapping("/users/edit/{id}")
    public String userEditPage(@PathVariable("id") int userId,Model model){

        model.addAttribute("userDto",usersService.findUserByUserId(userId));

        return "/users/edit";
    }

//    @PostMapping("/users/update")
//    public String userEditPage(@ModelAttribute UserDto userDto,Model model){
//
//        usersService.updateUserByIdWithoutPassword(userDto);
//
//        model.addAttribute("message", "수정되었습니다.");
//        model.addAttribute("userDto",usersService.findUserByUserId(userDto.getUserId()));
//
//        return "/users/edit";
//    }

    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable("id") int userId){

        usersService.deleteUserByUserId(userId);

        return "redirect:/admin/users";
    }



//여기서부터 어드민페이지 진짜구현

    @GetMapping("/users")
    //어드민 유저페이지 페이지네이션
    public String adminUserMainPage(@RequestParam(name ="page", defaultValue = "1")int page,
                                @RequestParam(name ="size", defaultValue = "10")int size,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) Boolean enabled,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                @RequestParam(required = false) Integer bookCount,
                                Model model){

        //페이지에 보여줄 유저 정보 가져오기
        PageDto<UserResponseDto> userPageDto = usersService.searchUsersWithBookCount(page, size, name, email, enabled, startDate, endDate, bookCount);

        // 모델에 데이터 전달
        model.addAttribute("userPageDto",userPageDto);

        // 검색 필드 값 모델에 추가
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("enabled", enabled);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("bookCount", bookCount);

        return "/users/main";
    }


    @GetMapping("/users/host")
    //어드민 유저페이지 페이지네이션
    public String adminUserHostPage(@RequestParam(name ="page", defaultValue = "1")int page,
                                    @RequestParam(name ="size", defaultValue = "10")int size,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) Boolean enabled,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                    @RequestParam(required = false) Integer bookCount,
                                    @RequestParam(required = false) Boolean isVerified,
                                    Model model){

        //페이지에 보여줄 유저 정보 가져오기
        PageDto<HostResponseDto> hostPageDto = usersService.searchHostsWithRoomCount(page, size, name, email, enabled, startDate, endDate, bookCount, isVerified);

        // 모델에 데이터 전달
        model.addAttribute("hostPageDto",hostPageDto);

        // 검색 필드 값 모델에 추가
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("enabled", enabled);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("bookCount", bookCount);

        return "/users/host";
    }

    @GetMapping("/users/detail/{id}")
    public String userDetail(@PathVariable("id") int userId,Model model){

        model.addAttribute("userDto",usersService.findUserByUserId(userId));
        model.addAttribute("hostDto",usersService.findHostByUserId(userId));


        return "/users/user-detail";
    }

    @GetMapping("/users/report")
    public String userReports(@RequestParam(value = "page", defaultValue = "1") int page,       // 기본값 1
                              @RequestParam(value = "size", defaultValue = "10") int size,     // 기본값 10
                              @RequestParam(required = false) String reporterEmail,
                              @RequestParam(required = false) String reportedUserEmail,
                              @RequestParam(required = false) String reportReason,
                              @RequestParam(required = false) String status,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startReportDate,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endReportDate,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startProcessedAt,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endProcessedAt,
                              Model model){

        // 서비스 호출하여 데이터 가져오기
        PageDto<UserReportDto> pageDto = usersService.searchUserReports(
                page, size, reporterEmail, reportedUserEmail, reportReason, status,
                startReportDate, endReportDate, startProcessedAt, endProcessedAt);

        // 모델에 페이징 데이터 전달
        model.addAttribute("pageDto", pageDto);

        model.addAttribute("reporterEmail", reporterEmail);
        model.addAttribute("reportedUserEmail", reportedUserEmail);
        model.addAttribute("reportReason", reportReason);
        model.addAttribute("status", status);
        model.addAttribute("startReportDate", startReportDate);
        model.addAttribute("endReportDate", endReportDate);
        model.addAttribute("startProcessedAt", startProcessedAt);
        model.addAttribute("endProcessedAt", endProcessedAt);

        return "users/report";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute UserDto userDto, Model model) {
        try {
            usersService.updateUser(userDto);
            model.addAttribute("message", "유저 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            model.addAttribute("message", "유저 정보 수정에 실패했습니다.");
        }

        return "redirect:/admin/users/detail/" + userDto.getUserId();
    }

    @PostMapping("/users/hosts/update")
    public String updateHost(@ModelAttribute HostDto hostDto, Model model) {
        usersService.updateHost(hostDto);
        model.addAttribute("message", "호스트 정보가 성공적으로 수정되었습니다.");
        return "redirect:/admin/users/detail/" + hostDto.getUserId();
    }


}