package com.minbak.web.board.posts;

import com.minbak.web.board.categories.BoardCategoriesService;
import com.minbak.web.board.categories.BoardCategoryDto;
import com.minbak.web.board.comments.BoardCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
