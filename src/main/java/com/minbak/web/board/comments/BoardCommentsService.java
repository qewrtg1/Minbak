package com.minbak.web.board.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCommentsService {

    @Autowired
    BoardCommentsMapper boardCommentsMapper;

    public List<BoardCommentDto> findCommentsByPostId(int postId){
        return boardCommentsMapper.findCommentsByPostId(postId);
    }

    public void createComment(BoardCommentDto boardCommentDto){
        boardCommentsMapper.createComment(boardCommentDto);
    }
}
