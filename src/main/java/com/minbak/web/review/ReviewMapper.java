package com.minbak.web.review;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface ReviewMapper {

    // 리뷰 목록 조회
    List<ReviewDto> findAllReview();

    // 리뷰 순서대로 목록 조회
    List<ReviewDto> findOrderedReview();

    List<Map<String, Object>> getReviewScore();

    // 검색어에 맞는 리뷰 리스트를 가져오는 메서드
    List<ReviewDto> searchReview(@Param("offset") int offset, @Param("limit") int limit, @Param("search") String search);

    // 검색어에 맞는 총 후기 수를 가져오는 메서드
    Integer getTotalReviewCount(@Param("search") String search);

    /** 페이지네이션을 적용하여 리뷰 리스트 조회*/
    List<ReviewDto> getReviews(@Param("offset") int offset, @Param("limit") int limit);

    // 리뷰 추가
    void createReview(ReviewDto reviewDto);

    // 리뷰 수정
    void editReview(ReviewDto review);

    // 리뷰 삭제
    void deleteReview(int id);

    // 블라인드 처리 기능 추가
    void blindReview(@Param("reviewId") int reviewId);

    void unblindReview(@Param("reviewId") int reviewId);

    // 부적절 리뷰 처리 기능 추가
    void markAsInappropriate(@Param("reviewId") int reviewId);

    // 리뷰 id랑 리뷰 order 받아서 리뷰 순서 업데이트
    void editReviewOrder(int id);

    // 특정 리뷰 조회
    ReviewDto findReviewById(int reviewId);






//    // 호스트가 답변해야 할 리뷰 목록을 가져오는 메서드
//    List<ReviewDto> findUnansweredReviewsByHost(int hostId);
//
//    // 호스트가 특정 리뷰에 답변을 추가하는 메서드
//    void updateHostReply(@Param("reviewId") int reviewId, @Param("hostReply") String hostReply);

}
