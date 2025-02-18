package com.minbak.web.review;

import com.minbak.web.board.categories.BoardCategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    // 리뷰 목록 조회
    List<ReviewDto> findAllReview();

    // 리뷰 순서대로 목록 조회
    List<ReviewDto> findOrderedReview();

    /** 페이지네이션을 적용하여 리뷰 리스트 조회*/
    List<ReviewDto> findReviewsWithPagination(@Param("offset") int offset, @Param("limit") int limit);

    /** 전체 리뷰 개수 조회*/
    int getTotalReviewCount();

    // 리뷰 추가
    void createReview(ReviewDto reviewDto);

    // 리뷰 수정
    void updateReview(ReviewDto review);

    // 리뷰 삭제
    void deleteReview(int id);

    //리뷰 id랑 리뷰 order 받아서 리뷰 순서 업데이트
    void updateReviewOrder(Integer id, int order);

    // 특정 리뷰 조회
    ReviewDto findReviewById(int reviewId);


}
