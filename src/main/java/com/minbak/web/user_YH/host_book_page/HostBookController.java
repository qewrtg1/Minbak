package com.minbak.web.user_YH.host_book_page;

import com.minbak.web.spring_security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/host/books")
public class HostBookController {
    private final HostBookService hostBookService;

    @GetMapping
    public String getReservations(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<HostBooksResponse> reservations = hostBookService.findBooksByUserId(userDetails.getUserId());

        model.addAttribute("books", reservations);
        return "/host-pages/host-book-page";
    }
}
