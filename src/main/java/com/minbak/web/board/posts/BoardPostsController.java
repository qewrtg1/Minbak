package com.minbak.web.board.posts;

import com.minbak.web.board.categories.BoardCategoriesService;
import com.minbak.web.board.categories.BoardCategoryDto;
import com.minbak.web.board.comments.BoardCommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/post")
public class BoardPostsController {

    @Autowired
    BoardPostsService boardPostsService;

    @Autowired
    BoardCommentsService boardCommentsService;

    @Autowired
    BoardCategoriesService boardCategoriesService;

    //BoardPostsController내의 모든 메서드에 model추가(카테고리불러오기)
    @ModelAttribute("boardCategories")
    public List<BoardCategoryDto> addGlobalMessage() {
        return boardCategoriesService.findOrderedCategories();
    }

    @GetMapping("/{id}")
    //게시글 상세보기
    public String postDetail(@PathVariable("id") int id, Model model){

        //게시글 정보 가져와서 model로 전달
        model.addAttribute("boardPost",boardPostsService.findPostById(id)) ;

        //해당 게시글의 댓글정보 가져와서 model로 전달
        model.addAttribute("boardComments", boardCommentsService.findCommentsByPostId(id)) ;

        return "/board/user/post";
    }

    
    @GetMapping("/delete/{id}")
    //게시글 삭제
    public String deletePost(@PathVariable("id") int id){

        //게시글 찾아서 제거
        boardPostsService.deletePost(id);
        
        //게시판홈으로
        return "redirect:/board";
    }

    @GetMapping("/create")
    //게시글 작성 페이지
    public String createPostPage(){
        return "/board/user/create-post";
    }

    @PostMapping("/create")
    //게시글 생성
    public String createPost(@ModelAttribute @Valid BoardPostDto boardPostDto, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){

            model.addAttribute("message", "백앤드 유효성검사 통과 실패!" );

            return "/board/user/error-page";

        }else {
            //게시글 추가
            boardPostsService.createPost(boardPostDto);

            //추가한 게시글페이지 불러오기(객체참조사용)
            return "redirect:/board/post/"+boardPostDto.getId();
        }

    }
}
