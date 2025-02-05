package com.minbak.web.board.comments;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardCommentsMapper {

    // 특정 게시글의 댓글 목록 조회
    List<BoardCommentDto> findCommentsByPostId(int postId);

    // 댓글 추가
    void createComment(BoardCommentDto comment);

    // 댓글 수정
    void updateComment(BoardCommentDto comment);

    // 댓글 삭제
    void deleteComment(int id);

    // 특정 댓글 조회
    BoardCommentDto findCommentById(int id);

}
