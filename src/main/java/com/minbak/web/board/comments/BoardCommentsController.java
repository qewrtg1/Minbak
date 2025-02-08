package com.minbak.web.board.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/comment")
public class BoardCommentsController {

    @Autowired
    BoardCommentsService boardCommentsService;

    @PostMapping("/create")
    public String createBoardComment(@ModelAttribute BoardCommentDto boardCommentDto){
        boardCommentsService.createComment(boardCommentDto);
        return "redirect:/board/post/"+boardCommentDto.getPostId();
    }

}
