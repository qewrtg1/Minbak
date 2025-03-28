package com.minbak.web.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 목록 페이지 (기본 1페이지, 5개씩 출력)
    @GetMapping
    public String getReviews(
            @RequestParam(name="page", defaultValue = "1") int page,  // 현재 페이지 (기본값: 1)
            @RequestParam(name ="size", defaultValue = "5") int size, // 한 페이지당 리뷰 개수 (기본값: 5)
            Model model) { //Thymeleaf에서 사용할 데이터를 모델에 담아 전달


        List<ReviewDto> review = reviewService.getReviews(page*5-size, size); // 리뷰 목록 가져오기
        Integer totalReview = reviewService.getTotalReviewCount("");  // 검색된 리뷰의 총 개수를 받아옴
        ReviewPageDto<ReviewDto> reviewPageDto = new ReviewPageDto<>(page,size,totalReview,review);
        model.addAttribute("reviewPageDto", reviewPageDto); // 모델에 데이터를 담아 뷰로 전달
        model.addAttribute("totalReview", totalReview);  // 총 리뷰 개수
        return "review/review"; // Thymeleaf 리뷰 페이지 반환
    }

    @GetMapping("/search")
    public String searchReview(
            @RequestParam(name="page", defaultValue = "1") int page,  // 현재 페이지 (기본값: 1)
            @RequestParam(name ="size", defaultValue = "5") int size, // 한 페이지당 리뷰 개수 (기본값: 5)
            @RequestParam(value = "search", defaultValue = "") String search,  // 검색어 파라미터를 받아옴, 기본값은 빈 문자열
            Model model) { // Thymeleaf에서 사용할 데이터를 모델에 담아 전달

        List<ReviewDto> reviews = reviewService.searchReview(page*5-size, size, search);  // 서비스로부터 검색된 리뷰 리스트를 받아옴
        Integer totalReview = reviewService.getTotalReviewCount(search);  // 검색된 리뷰의 총 개수를 받아옴
        ReviewPageDto<ReviewDto> reviewPageDto = new ReviewPageDto<>(page,size,totalReview,reviews);
        model.addAttribute("reviewPageDto", reviewPageDto);
        model.addAttribute("totalReview", totalReview);  // 총 리뷰 개수
        return "review/review"; // Thymeleaf 리뷰 페이지 반환
    }

    @GetMapping("/detail/{id}"  )
    public String reviewDetail(@PathVariable ("id") int id, Model model){
        ReviewDto review = reviewService.findReviewById(id);
        model.addAttribute("review", review); // "reviews" 라는 이름으러 템플릿에 전달

        return  "review/review-detail"; // review.html 파일을 반환
    }


    // 업데이트 클릭시 상세보기로 리다이렉팅
    @PostMapping("/edit")
    public String editReview(ReviewDto review) {
        reviewService.editReview(review);
        return "redirect:/admin/review/detail/" + review.getReviewId();
    }

    // 해당 리뷰 삭제하기 삭제 후 리뷰 리스트 페이지로
    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") int id, Model model){
        reviewService.deleteReview(id);
        return "redirect:/admin/review";  // review.html 파일을 렌더링
    }


    // 블라인드 처리 기능 (새로운 기능 추가)
    @PostMapping("/blind/{id}")
    public String blindReview(@PathVariable("id") int id, @RequestParam("isBlinded") int isBlind) {
        System.out.println(isBlind);
        reviewService.blindReview(id, isBlind);
        return "redirect:/admin/review";
    }

    // 부적절한 리뷰 표시 기능 (새로운 기능 추가)
    @PostMapping("/inappropriate/{id}")
    public String markAsInappropriate(@PathVariable("id") int id) {
        reviewService.markAsInappropriate(id);
        return "redirect:/admin/review";
    }



}