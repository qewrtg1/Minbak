package com.minbak.web.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequestMapping("/admin/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String adminReviewPage(Model model){
        // 모든 리뷰 목록을 서비스에서 가져와서 모델에 추가
        List<ReviewDto> review = reviewService.findAllReview();
        model.addAttribute("review", review);  // "reviews"라는 이름으로 템플릿에 전달
        return "review/review";  // review.html 파일을 렌더링
    }
}