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

    // 리뷰 수정시 페이지를 보여주는 메서드
    @GetMapping("/edit/{id}")
    public String reviewUpdate(@PathVariable("id") int id, Model model){
        ReviewDto review = reviewService.findReviewById(id);
//        if (review == null) {
//            throw new ReviewException("데이터를 찾을 수 없습니다.");
//        }
        model.addAttribute("review", review);     // 모델에 가져온 리뷰 정보를 추가하여 뷰에 전달
        return "review/review-edit"; // 'review/review-edit' 뷰로 이동, 리뷰 수정 페이지를 렌더링
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
        List<ReviewDto> review = reviewService.findAllReview();
        model.addAttribute("reviews", review);  // "review"라는 이름으로 템플릿에 전달
        return "review/review";  // review.html 파일을 렌더링
    }



    // 호스트가 아직 답변하지 않은 리뷰 목록을 조회하는 API
    @GetMapping("/unanswered-reviews")  // '/host/unanswered-reviews' 요청을 처리
    public List<ReviewDto> getUnansweredReviews(@RequestParam int hostId) {
        return reviewService.getUnansweredReviews(hostId);  // 서비스 호출
    }

    // 특정 리뷰에 답변을 추가하는 API
    @PostMapping("/add-reply")  // '/host/add-reply' 요청을 처리
    public void addHostReply(@RequestParam int reviewId, @RequestParam String hostReply) {
        reviewService.addHostReply(reviewId, hostReply);  // 서비스 호출
    }




    // 리뷰 작성
    @PostMapping("/create")
    public String createReview(@RequestBody ReviewDto reviewDto, @RequestParam int bookId, @RequestParam int userId) {
        // User가 체크아웃 후 2주일 이내에만 리뷰 작성 가능
        if (!reviewService.canUserWriteReview(bookId, userId)) {
            return "리뷰는 체크아웃 후 2주 이내에만 작성할 수 있습니다.";
        }

        reviewService.createReview(reviewDto, bookId, userId);  // 리뷰 생성
        return "리뷰가 작성되었습니다.";
    }

    // 호스트가 리뷰에 답변
    @PostMapping("/reply")
    public String addHostReply(@RequestParam int reviewId, @RequestParam String hostReply) {
        // 호스트가 답변할 수 있는지 확인
        if (!reviewService.canHostReply(reviewId)) {
            return "호스트는 리뷰 작성 후 2주 이내에만 답변할 수 있습니다.";
        }

        reviewService.addHostReply(reviewId, hostReply);  // 호스트 답변 추가
        return "답변이 추가되었습니다.";
    }

    // 리뷰가 공개 가능한지 확인
    @GetMapping("/public/{reviewId}")
    public boolean isReviewPublic(@PathVariable int reviewId) {
        return reviewService.isReviewPublic(reviewId);  // User와 Host가 모두 작성했을 때 공개 여부 반환
    }


}