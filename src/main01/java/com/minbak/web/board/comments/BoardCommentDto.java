package com.minbak.web.board.comments;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardCommentDto {

    private Integer id;          // 댓글 ID
    private int postId;          // 해당 댓글이 속한 게시글 ID
    private String author;       // 댓글 작성자
    private String content;      // 댓글 내용
    private LocalDateTime createdAt; // 댓글 생성일
    private LocalDateTime updatedAt; // 댓글 수정일

}