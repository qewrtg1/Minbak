package com.minbak.web.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {

    private Integer reviewId;  // 리뷰 ID (Primary Key)

    private int userId;           // 리뷰 작성자 ID
    private int accommodationId;  // 민박/숙소 ID
    private int rating;        // 평점 (1~5)
    private String comment;        // 리뷰 내용

    private LocalDateTime createdAt; // 리뷰 작성 일시
    private LocalDateTime updatedAt; // 리뷰 수정 일시

}