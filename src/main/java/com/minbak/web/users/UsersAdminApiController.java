package com.minbak.web.users;

import com.minbak.web.payments.PaymentDto;
import com.minbak.web.rooms.RoomsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/api")
@RequiredArgsConstructor
@ResponseBody
public class UsersAdminApiController {

    private final UsersService usersService;

//    @GetMapping("/users")
//    public UserPageDto<UserResponseDto> searchUsers(
//            @RequestParam("search") String search,    // 검색어
//            @RequestParam("page") int page,           // 현재 페이지
//            @RequestParam("size") int size            // 페이지 크기
//    ) {
//
//        return usersService.findUsersByLimitAndOffsetAndString(page, size, search);
//
//    }

    @GetMapping("/users/payments")
    public UserPageDto<PaymentDto> getPaymentByUserPageing(
            @RequestParam("userId") int userId,    // 검색어
            @RequestParam("page") int page,           // 현재 페이지
            @RequestParam("size") int size            // 페이지 크기
    ){
        return usersService.findPaymentsByLimitAndOffsetAndUserId(page, size, userId);
    }

    @GetMapping("/users/rooms")
    public UserPageDto<RoomsDto> getRoomsByUserPageing(
            @RequestParam("userId") int userId,    // 검색어
            @RequestParam("page") int page,           // 현재 페이지
            @RequestParam("size") int size            // 페이지 크기
    ){
        return usersService.findRoomsByLimitAndOffsetAndUserId(page, size, userId);
    }
}
