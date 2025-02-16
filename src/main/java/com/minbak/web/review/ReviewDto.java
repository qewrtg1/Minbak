package com.minbak.web.review;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {

    private Integer reviewId;   // 리뷰 고유 ID

    private int userId;     // 작성한 회원 ID
    private int bookId;     // 리뷰 대상 예약 ID
    private String content; // 리뷰 내용
    private double score;      // 리뷰 점수

//    private LocalDateTime createdAt; // 리뷰 작성 일시
//    private LocalDateTime updatedAt; // 리뷰 수정 일시

    @Override
    public String toString() {
        return "ReviewDto{" +
                "reviewId=" + reviewId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", content='" + content + '\'' +
                ", score=" + score +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
                '}';
    }

}