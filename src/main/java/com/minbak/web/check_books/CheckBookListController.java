package com.minbak.web.check_books;

import com.minbak.web.books.BooksPageDto;
import com.minbak.web.check_books.dto.CheckBookDto;
import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class CheckBookListController {
    @Autowired
   CheckBookService checkBookService;

    @GetMapping("/book/list")
    public String checkBookList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestParam(name="page", defaultValue = "1") int page,
                                @RequestParam(name="size", defaultValue = "10") int size,
            Model model){

        BooksPageDto<CheckBookDto> booksPageDto=checkBookService.getBooks(page,size,userDetails.getUserId());

        for(int i = 0;i<booksPageDto.getObjects().size();i++){

            booksPageDto.getObjects().get(i).setRoomUrl(checkBookService.findRoomImageUrlByRoomId(booksPageDto.getObjects().get(i).getRoomId()));

        }
        for(int i = 0;i<booksPageDto.getObjects().size();i++){

            booksPageDto.getObjects().get(i).setUser(checkBookService.findUserByUserId(booksPageDto.getObjects().get(i).getUserId()));

        }

         model.addAttribute("booksPageDto",booksPageDto);

        return "/user-pages/user-book-list";
    }

    @GetMapping("/book/detail/{bookId}")
    public String checkBookListDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestParam(name = "bookId") int bookId,
                                      Model model){

        CheckBookDto checkBookDto=checkBookService.findBookByBookId(bookId);
        checkBookDto.setRoomUrls(checkBookService.findRoomImageUrlsByRoomId(checkBookService.findBookByBookId(bookId).getRoomId()));
        model.addAttribute("book",checkBookDto);


        return "/user-pages/user-book-detail";
    }
}
