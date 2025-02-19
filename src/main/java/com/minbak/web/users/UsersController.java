package com.minbak.web.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/users")
    //어드민 메인페이지 페이지네이션
    public String adminMainPage(@RequestParam(name ="page", defaultValue = "1")int page,
                                @RequestParam(name ="size", defaultValue = "10")int size,
                                Model model){

        //페이지에 보여줄 유저 정보 가져오기
        UserPageDto<UserResponseDto> userPageDto = usersService.findUsersByLimitAndOffset(page, size);

        //호스트 숫자 가져오기
        int allHostNum = usersService.countUserRolesByRoleId(2);

        //오늘 가입한 가입자 수 가져오기
        int UsersJoinedTodayNum = usersService.countUsersJoinedToday();

        //모든 관리자 수 가져오기
        int allAdminNum = usersService.countUserRolesByRoleId(3);

        //이번 주 요일별 가입자 수
        // Map<Integer, Integer> 형태로 결과를 가져옴 0 = 일요일 6 = 토요일
        List<Map<Integer, Integer>> weekdayUserCounts = usersService.countUsersJoinedByWeekday();

        model.addAttribute("usersByWeekday", weekdayUserCounts);

        model.addAttribute("allAdminNum", allAdminNum);
        model.addAttribute("UsersJoinedTodayNum", UsersJoinedTodayNum);
        model.addAttribute("allHostNum", allHostNum);
        model.addAttribute("userPageDto",userPageDto);
        // 모델에 데이터 전달

        return "/users/user-main";
    }

    @GetMapping("/users/edit/{id}")
    public String userEditPage(@PathVariable("id") int userId,Model model){

        model.addAttribute("userDto",usersService.findUserByUserId(userId));

        return "/users/edit";
    }

    @PostMapping("/users/update")
    public String userEditPage(@ModelAttribute UserDto userDto,Model model){

        usersService.updateUserByIdWithoutPassword(userDto);

        model.addAttribute("message", "수정되었습니다.");
        model.addAttribute("userDto",usersService.findUserByUserId(userDto.getUserId()));

        return "/users/edit";
    }

    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable("id") int userId){

        usersService.deleteUserByUserId(userId);

        return "/users/detail/{id}";
    }

    @GetMapping("/users/detail/{id}")
    public String userDetail(@PathVariable("id") int userId,Model model){

        model.addAttribute("userDto",usersService.findUserByUserId(userId));

        return "/users/user-detail";
    }

}