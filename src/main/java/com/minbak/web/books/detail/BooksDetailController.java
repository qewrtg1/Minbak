package com.minbak.web.books.detail;

import com.minbak.web.books.BooksDto;
import com.minbak.web.books.BooksService;
import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.user_YH.RoomDetailService;
import com.minbak.web.user_YH.dto.RoomDetailDto;
import com.minbak.web.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/room")
@RequiredArgsConstructor
public class BooksDetailController {

    private final UsersService usersService;
    private final BooksService booksService;
    private final RoomDetailService roomDetailService;

    @GetMapping("/{roomId}/confirm")
    public String confirmReservation(@PathVariable int roomId,
                                     @RequestParam(required = false) LocalDate checkInDate,
                                     @RequestParam(required = false) LocalDate checkOutDate,
                                     @RequestParam(required = false, defaultValue = "1") int guestsNum,
                                     Model model,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 값이 없을 경우 기본값 설정
        if (checkInDate == null || checkOutDate == null) {
            checkInDate = LocalDate.now().plusDays(1);
            checkOutDate = LocalDate.now().plusDays(2);
        }

        model.addAttribute("user", usersService.findUserByUserId(userDetails.getUserId()));
        RoomDetailDto roomDetail = roomDetailService.getRoomDetail(roomId);
        model.addAttribute("room", roomDetail.getRoom());
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("guestCount", guestsNum);
        return "/books/reservation_confirm";
    }
}
