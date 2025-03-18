package com.minbak.web.review;

import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.spring_security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/review/host")
public class ReviewHostController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private Util util;

    @GetMapping("/{id}")
    public String writeReply(Model model,
     @AuthenticationPrincipal CustomUserDetails userDetails,
     @PathVariable("id") int reviewId){
        ReviewDto review = reviewService.findReviewById(reviewId);
        int hostId = review.getHostId();
        int userId = (int)reviewService.findUserIdByHostId(hostId);
        if(!util.checkAuthority(userDetails, userId)){
            throw new ReviewException("권한이 없습니다.");
        }
        model.addAttribute("review", review);
        return "review/review-reply";
    }

    @PostMapping("/{id}")
    public String replyReview (Model model, @PathVariable("id") int reviewId,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                              ReviewDto reviewDto) {
        int hostId = reviewDto.getHostId();
        int userId = (int)reviewService.findUserIdByHostId(hostId);
        if(!util.checkAuthority(userDetails, userId)){
            throw new ReviewException("답변 권한이 없습니다.");
        }

        Integer result = reviewService.replyReview(reviewDto);
        if (result == 0 ){
            throw new ReviewException("답변 작성에 실패했습니다.");
        }

        return "redirect:/host/today";
    }



    @PutMapping("/host/review/{id}")
    public String deleteReply(Model model, @PathVariable("id") int reviewId, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ReviewDto reviewDto) {
        int hostId = reviewDto.getHostId();
        if(!util.checkAuthority(userDetails, hostId)){
            throw new ReviewException("권한이 없습니다.");
        }
        Integer result = reviewService.deleteReview(reviewId);
        return "redirect:/host/review/{id}";
    }
}
