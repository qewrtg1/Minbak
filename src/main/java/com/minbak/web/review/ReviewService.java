package com.minbak.web.review;

import com.minbak.web.board.categories.BoardCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired  // Spring에서 자동으로 의존성 주입
    public ReviewService(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    // 검색어에 맞는 리뷰 리스트를 반환하는 메서드
    public List<ReviewDto> getReviews(int offset, int size) {  // 리턴 타입과 메서드 이름을 명확히 작성
        return reviewMapper.getReviews(offset, size);  // Mapper에서 검색된 리뷰 리스트를 반환
    }

    // 검색어에 맞는 총 후기 수를 반환하는 메서드
    public Integer getTotalReviewCount(String search) {  // 리턴 타입을 Integer로 명시
        return reviewMapper.getTotalReviewCount(search);  // Mapper에서 총 후기 수를 반환
    }


    // 검색어에 맞는 리뷰 리스트를 반환하는 메서드
    public List<ReviewDto> searchReview(int offset, int size, String search) {
        return reviewMapper.searchReview(offset, size, search) ;  // Mapper에서 검색된 리뷰 리스트를 반환
    }

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
    public void editReview(ReviewDto review) {
        ReviewDto existingReview = reviewMapper.findReviewById(review.getReviewId());
        if (existingReview == null) {
            throw new IllegalArgumentException("해당 리뷰가 존재하지 않습니다: " + review.getReviewId());
        }
        reviewMapper.editReview(review);
    }

    // 리뷰 삭제
    public void deleteReview(int id) {
        reviewMapper.deleteReview(id);
    }

    // 리뷰 추가
    public void createReview(ReviewDto reviewDto) {
        reviewMapper.createReview(reviewDto);
    }




    // 특정 호스트가 답변해야 할 리뷰 목록을 가져오는 서비스 메서드
    public List<ReviewDto> getUnansweredReviews(int hostUserId) {
        // 리뷰 목록을 가져오는 Mapper 메서드 호출
        return reviewMapper.findUnansweredReviewsByHost(hostUserId);  // 해당 호스트의 답변하지 않은 리뷰 리스트 반환
    }

    // 특정 리뷰에 대한 호스트의 답변을 저장하는 서비스 메서드
    public void addHostReply(int reviewId, String hostReply) {
        // 리뷰 ID와 답변 내용을 전달하여 Mapper 메서드 호출
        reviewMapper.updateHostReply(reviewId, hostReply);  // 해당 리뷰에 답변을 추가
    }

}


