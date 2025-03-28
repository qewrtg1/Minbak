package com.minbak.web.review;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {

    private Integer reviewId;  // 리뷰 고유 ID

    private int userId;        // 작성한 회원 ID
    private int bookId;        // 리뷰 대상 예약 ID
    private String content;    // 리뷰 내용
    private double score;      // 리뷰 점수
    private String email;      // 회원 이메일
    private int isBlinded; // 블라인드 여부 (true: 블라인드 처리됨, false: 정상 표시)


    private int hostId;        // 호스트 ID
    private String hostReply;  // 호스트 답글 (null 가능)

    private int roomId;        // Room ID

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt; // 리뷰 작성 일시

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt; // 수정 작성 일시
}