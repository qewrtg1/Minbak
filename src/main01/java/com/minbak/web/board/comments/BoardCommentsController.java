package com.minbak.web.board.comments;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board/comment")
public class BoardCommentsController {

    @Autowired
    BoardCommentsService boardCommentsService;

    @PostMapping("/create")
    //유효성검사 thymeleaf버전
    public String createBoardComment(@ModelAttribute @Valid BoardCommentDto boardCommentDto, BindingResult bindingResult, Model model){

        //bindingResult가 에러를 갖고있을때(유효성검사 실패시)
        if(bindingResult.hasErrors()){

            //실패메시지 전달
            model.addAttribute("message", "백앤드 유효성검사 통과 실패!" );

            //에러페이지 불러오기
            return "/board/user/error-page";

        }else {
            //댓글 추가
            boardCommentsService.createComment(boardCommentDto);

            //해당 댓글의 게시글페이지 리다이렉트
            return "redirect:/board/post/"+boardCommentDto.getPostId();
        }


    }

    @GetMapping("/delete/{id}")
    public String deleteBoardComment(@PathVariable("id") int id){

        //삭제하기 전에 댓글id로 해당 게시글 id가져오기
        int postId = boardCommentsService.findPostByCommentId(id).getId();
        
        //댓글 찾아서 제거
        boardCommentsService.deleteComment(id);

        //댓글id로 포스트 찾아와서 리다이렉트
        return "redirect:/board/post/"+postId;
    }

}
