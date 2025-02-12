package com.minbak.web.board.comments;

import com.minbak.web.board.posts.BoardPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCommentsService {

    @Autowired
    BoardCommentsMapper boardCommentsMapper;

    //게시글아이디로 댓글들 가져오기
    public List<BoardCommentDto> findCommentsByPostId(int postId){
        return boardCommentsMapper.findCommentsByPostId(postId);
    }

    //댓글 생성
    public void createComment(BoardCommentDto boardCommentDto){
        boardCommentsMapper.createComment(boardCommentDto);
    }

    //댓글 삭제
    public void deleteComment(int id){
        boardCommentsMapper.deleteComment(id);
    }

    //댓글id로 해당 게시글 가져오기
    public BoardPostDto findPostByCommentId(int id){

        return  boardCommentsMapper.findPostByCommentId(id);
    }

}
