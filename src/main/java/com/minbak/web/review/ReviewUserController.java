package com.minbak.web.review;

import com.minbak.web.books.BooksDto;
import com.minbak.web.review.ReviewException;
import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.spring_security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class ReviewUserController  {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Util util;


    @GetMapping("/user/books/review/{id}")
    public String writeReview(Model model,
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable("id") int bookId) {
        BooksDto booksDto = reviewService.getBookData(bookId);

        if (!util.checkAuthority(userDetails, booksDto.getUserId())) {
           throw new ReviewException("본인만이 리뷰 작성이 가능합니다.");
        }
        model.addAttribute("books", booksDto);
        return "review/review-write";
    }

    @PostMapping("/user/books/review/{id}")
    public String createReview (Model model, @PathVariable("id") int bookId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        ReviewDto reviewDto) { //Thymeleaf에서 사용할 데이터를 모델에 담아 전달
        if (!util.checkAuthority(userDetails, reviewDto.getUserId())) {
            throw new ReviewException("본인만이 리뷰 작성이 가능합니다.");
        }
        int result = reviewService.createReview(reviewDto);
        if (result == 0 ){
            throw new ReviewException("리뷰 작성에 실패했습니다.");
        }
        int roomId = reviewDto.getRoomId();
        return "redirect:/room/" + roomId;

    }

}
