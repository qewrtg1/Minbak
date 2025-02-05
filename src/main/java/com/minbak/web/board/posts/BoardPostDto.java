package com.minbak.web.board.posts;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardPostDto {

    private Integer id;     // 게시글 ID
    private String title;   // 게시글 제목
    private String content;     // 게시글 내용
    private String author;      // 작성자
    private String subject;     // 말머리 (선택 사항)
    private int categoryId;     // 카테고리 ID (외래 키)
    private String categoryName; // 카테고리 이름 (조인 결과)
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일

}