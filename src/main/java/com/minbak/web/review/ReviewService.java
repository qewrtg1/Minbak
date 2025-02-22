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

    @Autowired  // Spring에서 자동으로 의존성 주입
    public ReviewService(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    // 검색어에 맞는 리뷰 리스트를 반환하는 메서드
    public List<ReviewDto> getReviews(String search) {  // 리턴 타입과 메서드 이름을 명확히 작성
        return reviewMapper.selectReviews(search);  // Mapper에서 검색된 리뷰 리스트를 반환
    }

    // 검색어에 맞는 총 후기 수를 반환하는 메서드
    public Integer getTotalReviewCount(String search) {  // 리턴 타입을 int로 명시
        return reviewMapper.selectTotalReviewCount(search);  // Mapper에서 총 후기 수를 반환
    }

    // 모든 리뷰 목록 가져오기
    public List<ReviewDto> findAllReview() {
        return reviewMapper.findAllReview();
    }

    //모든 리뷰 순서대로 가져오기
    public List<ReviewDto> findOrderedReview() {
        return reviewMapper.findOrderedReview();
    }

    /** 페이지네이션을 적용하여 특정 개수의 리뷰를 조회*/
    public List<ReviewDto> getReviewsWithPagination(int offset, int limit) {
        return reviewMapper.findReviewsWithPagination(offset, limit); // MyBatis에서 페이징된 리뷰 가져오기
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

}
