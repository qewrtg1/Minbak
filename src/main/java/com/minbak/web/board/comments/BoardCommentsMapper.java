package com.minbak.web.board.comments;

import com.minbak.web.board.posts.BoardPostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardCommentsMapper {

    // 특정 게시글의 댓글 목록 조회
    List<BoardCommentDto> findCommentsByPostId(int postId);

    // 댓글 추가
    void createComment(BoardCommentDto comment);

    // 댓글 수정 사용X
    void updateComment(BoardCommentDto comment);

    // 댓글 삭제
    void deleteComment(int id);

    // 특정 댓글 조회 사용X
    BoardCommentDto findCommentById(int id);

    //댓글id로 해당댓글의 게시글 정보 가져오기
    //CommentMapper지만 xml은 사실상 table을 가리지 않음으로 사용
    BoardPostDto findPostByCommentId(int id);

}
