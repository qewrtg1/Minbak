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
        model.addAttribute("reviews", review);  // "reviews"라는 이름으로 템플릿에 전달
        return "review/review";  // review.html 파일을 반환

    }

    @GetMapping("/detail/{id}"  )
    public String reviewDetail(@PathVariable ("id") int id, Model model){
        ReviewDto review = reviewService.findReviewById(id);
        model.addAttribute("review", review); // "reviews" 라는 이름으러 템플릿에 전달

     return  "review/review-detail"; // review.html 파일을 반환
    }

    @GetMapping("/update/{id}")
    public String reviewUpdate(@PathVariable("id") int id, Model model){
        ReviewDto review = reviewService.findReviewById(id);
        model.addAttribute("review", review);
        return "review/review-update";
    }

    @PostMapping("/update")
    public String updateReview(@ModelAttribute ReviewDto review) {
        System.out.println("🔍 리뷰 업데이트 요청: " + review); // 디버깅용 로그 추가

        if (review.getReviewId() == null) {
            throw new IllegalArgumentException("리뷰 ID가 없습니다.");
        }

        reviewService.updateReview(review);
        return "redirect:/admin/review/detail/" + review.getReviewId();
    }

    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") int id, Model model){
        reviewService.deleteReview(id);
        List<ReviewDto> review = reviewService.findAllReview();
        model.addAttribute("reviews", review);  // "review"라는 이름으로 템플릿에 전달
        return "review/review";  // review.html 파일을 렌더링
    }
}