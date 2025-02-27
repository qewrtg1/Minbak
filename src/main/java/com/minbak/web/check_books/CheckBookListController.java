package com.minbak.web.check_books;

import com.minbak.web.books.BooksDto;
import com.minbak.web.books.BooksPageDto;
import com.minbak.web.books.BooksService;
import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CheckBookListController {
    @Autowired
   CheckBookService checkBookService;

    @GetMapping("/checkBookList")
    public String checkBookList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestParam(name="page", defaultValue = "1") int page,
                                @RequestParam(name="size", defaultValue = "10") int size,
            Model model){
        BooksPageDto<BooksDto> booksPageDto=checkBookService.getBooks(page,size,userDetails.getUserId());
         List<BooksDto> booksList=booksPageDto.getObjects();
         model.addAttribute("booksList",booksList);
         model.addAttribute("booksPageDto",booksPageDto);




        return "/checkBooks/checkBookList";
    }
}
