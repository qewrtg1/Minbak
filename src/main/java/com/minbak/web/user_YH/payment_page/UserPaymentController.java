package com.minbak.web.user_YH.payment_page;

import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@Controller
public class UserPaymentController {

    @Autowired
    private UserPaymentService userPaymentService;

    @GetMapping("/book/pay/{bookId}")
    public String showBookingCheckout(@PathVariable("bookId") int bookId, Model model,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 예약 정보 조회
        PaymentBookDto book = userPaymentService.getPaymentBookDetails(bookId);
        PaymentRoomDto room = userPaymentService.getPaymentRoomDetails(book.getRoomId());
        PaymentUserDto user = userPaymentService.getPaymentUserDetails(book.getUserId());


        if (book.getStatus().equals("완료")){
            // todo 이미 예약된 숙소인 경우 코딩
        }

        if(userDetails.getUserId() == book.getUserId()){
            BigDecimal totalAmount = room.getPrice().multiply(BigDecimal.valueOf(book.getNights()));

            // 모델에 데이터 추가
            model.addAttribute("book", book);
            model.addAttribute("room", room);
            model.addAttribute("user", user);
            model.addAttribute("totalAmount",totalAmount);
            model.addAttribute("roomImageUrl",userPaymentService.getRoomImageUrl(book.getRoomId()));

            return "/user-pages/user-payment"; // checkout.html 페이지로 이동
        }else {
            return "redirect:/room/1";
        }

    }
}
