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
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BooksDetailController {

    private final UsersService usersService;
    private final BooksService booksService;
    private final RoomDetailService roomDetailService;

    @GetMapping("/room/{roomId}/confirm")
    public String confirmReservation(@PathVariable int roomId,
                                     @RequestParam LocalDate checkInDate,
                                     @RequestParam LocalDate checkOutDate,
                                     @RequestParam int guestsNum,
                                     Model model,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 값이 없을 경우 기본값 설정
        if (checkInDate == null || checkOutDate == null) {
            checkInDate = LocalDate.now().plusDays(1);
            checkOutDate = LocalDate.now().plusDays(2);
        }

        int nights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        model.addAttribute("user", usersService.findUserByUserId(userDetails.getUserId()));
        RoomDetailDto roomDetail = roomDetailService.getRoomDetail(roomId);

        System.out.println(roomDetail.getRoom().getPricePerNight());
        model.addAttribute("nights",nights );
        model.addAttribute("room", roomDetail.getRoom());
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("guestCount", guestsNum);
        return "/books/reservation_confirm";
    }

    @GetMapping("/booking")
    public String getBookingPage(@ModelAttribute BooksDto booksDto, Model model) {

        booksService.createBook(booksDto);

        model.addAttribute("message", "예약되었습니다.");
        return "redirect:/user/book/list"; // 예약 확인 페이지로 이동 (bookingConfirmation.html)
    }
}
