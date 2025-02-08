package com.minbak.web.board;

import com.minbak.web.board.categories.BoardCategoriesService;
import com.minbak.web.board.categories.BoardCategoryDto;
import com.minbak.web.board.posts.BoardPostDto;
import com.minbak.web.board.posts.BoardPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardMainController {

    @Autowired
    BoardCategoriesService boardCategoriesService;

    @Autowired
    BoardPostsService boardPostsService;

    @GetMapping
    public String boardMainPage(@RequestParam(name ="page", defaultValue = "1")int page,
                                @RequestParam(name ="size", defaultValue = "5")int size,
                                @RequestParam(name ="category", defaultValue = "0") int categoryId,
                                Model model){

        //카테고리 목록받아서 사이드에 보여주고, 게시글 정보를 받아서 BoardPageDto객체에 넣고 BoardPageDto 전달.

        //카테고리 목록 받아서 model로 전달
        List<BoardCategoryDto> boardCategories= boardCategoriesService.findOrderedCategories();
        model.addAttribute("boardCategories",boardCategories);

        if(categoryId == 0){

            //모든 게시글 수 받아오기
            int totalItems = boardPostsService.countAllPosts();

            //page,size로 해당 페이지에 보여줄 게시글 가져온 후 BoardPageDto 생성 후 model로 전달
            List<BoardPostDto> boardPosts = boardPostsService.findPostsByLimitAndOffset(page,size);
            BoardPageDto<BoardPostDto> boardPageDto = new BoardPageDto<>(page,size,totalItems,boardPosts);
            model.addAttribute("boardPageDto",boardPageDto);

        }else {
            //해당 카테고리의 모든 게시글 수 받아오기
            int totalItems = boardPostsService.countAllPostsByCategoryId(categoryId);
            
            //카테고리의 게시글이 0개면 error-page리턴
            if(totalItems == 0){
                model.addAttribute("message","카테고리가 없습니다.");
                return "/board/user/error-page";
            }

            //해당하는 카테고리의 게시글을 가져온 후 BoardPageDto 생성 후 model로 전달
            List<BoardPostDto> boardPosts = boardPostsService.findPostsByLimitAndOffsetByCategoryId(page,size,categoryId);
            BoardPageDto<BoardPostDto> boardPageDto = new BoardPageDto<>(page,size,totalItems,boardPosts);
            model.addAttribute("boardPageDto",boardPageDto);

            //카테고리 이름 보내주기
            model.addAttribute("categoryName",boardCategoriesService.findCategoryById(categoryId).getName());
        }

        //categoryId 전달
        model.addAttribute("categoryId",categoryId);
        return "/board/user/main";
    }

    @GetMapping("/admin")
    public String adminMainPage(){
        return "/board/admin/main";
    }


}
