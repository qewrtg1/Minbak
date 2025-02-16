package com.minbak.web.review;

import com.minbak.web.board.categories.BoardCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    // 모든 리뷰 목록 가져오기
    public List<ReviewDto> findAllReview() {
        return reviewMapper.findAllReview();
    }

    //모든 리뷰 순서대로 가져오기
    public List<ReviewDto> findOrderedReview() {
        return reviewMapper.findOrderedReview();
    }

    // 리뷰 상세보기
    public ReviewDto findReviewById(int reviewId) {
        return reviewMapper.findReviewById(reviewId);
    }

    // 리뷰 수정 ()
    public void updateReview(ReviewDto review) {
        ReviewDto existingReview = reviewMapper.findReviewById(review.getReviewId());
        if (existingReview == null) {
            throw new IllegalArgumentException("해당 리뷰가 존재하지 않습니다: " + review.getReviewId());
        }

        reviewMapper.updateReview(review);
    }

    // 리뷰 삭제
    public void deleteReview(int id) {
        reviewMapper.deleteReview(id);
    }

    // 리뷰 추가
    public void createReview(ReviewDto reviewDto) {
        reviewMapper.createReview(reviewDto);
    }

}