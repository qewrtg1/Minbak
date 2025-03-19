package com.minbak.web.user_YH.host_book_page;

import com.minbak.web.spring_security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HostBookController {
    private final HostBookService hostBookService;

    @GetMapping("/host/books")
    public String getReservations(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Integer userId = userDetails.getUserId();

        List<HostBooksResponse> books = hostBookService.findBooksByUserId(userId);
        List<Map<String, String>> rooms = hostBookService.getHostingRooms(userId);
        model.addAttribute("rooms", rooms);

        model.addAttribute("user",hostBookService.findUserDetailByUserId(userId));
        model.addAttribute("books", books);
        return "host-pages/host-book-page";
    }

    @PutMapping("/host/approve/{bookId}")
    public ResponseEntity<String> approveReservation(@PathVariable("bookId") Integer bookId) {
        hostBookService.approveReservation(bookId);
        return ResponseEntity.ok("예약이 승인되었습니다.");
    }

    @PutMapping("/host/decline/{bookId}")
    public ResponseEntity<String> declineReservation(@PathVariable("bookId") Integer bookId) {
        hostBookService.declineReservation(bookId);
        return ResponseEntity.ok("예약이 거절되었습니다.");
    }
}
