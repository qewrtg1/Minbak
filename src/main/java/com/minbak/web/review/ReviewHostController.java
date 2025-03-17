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
@RequestMapping
public class ReviewHostController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private Util util;

    @GetMapping("/host/review/{id}")
    public String getUnrepliedReview(Model model,
                                     @AuthenticationPrincipal CustomUserDetails userDetails,
                                     @PathVariable("id") int hostId){
        if(!util.checkAuthority(userDetails, hostId)){
            throw new ReviewException("권한이 없습니다.");
        }

        List<ReviewDto> reviews = reviewService.getUnrepliedReview(hostId);
        model.addAttribute("reviews", reviews);
        return "review/unreplied_review";
    }

    @PostMapping("/host/review/{id}")
    public String replyReview (Model model, @PathVariable("id") int reviewId,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestBody ReviewDto reviewDto) {
        if(!util.checkAuthority(userDetails, reviewDto.getHostId())){
            throw new ReviewException("권한이 없습니다.");
        }

        Integer result = reviewService.replyReview(reviewId);
        return "redirect:/host/review/{id}";
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
