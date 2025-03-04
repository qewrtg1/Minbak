package com.minbak.web.check_books;

import com.minbak.web.books.BooksPageDto;
import com.minbak.web.check_books.dto.CheckBookDto;
import com.minbak.web.check_books.dto.CheckBookHostDto;
import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.user_YH.RoomDetailMapper;
import com.minbak.web.user_YH.dto.DetailHostResponse;
import com.minbak.web.users.HostDto;
import com.minbak.web.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class CheckBookListController {
    @Autowired
   CheckBookService checkBookService;
    @Autowired
    RoomDetailMapper roomDetailMapper;

    @GetMapping("/book/list")
    public String checkBookList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestParam(name="page", defaultValue = "1") int page,
                                @RequestParam(name="size", defaultValue = "10") int size,
            Model model){

        BooksPageDto<CheckBookDto> booksPageDto=checkBookService.getBooks(page,size,userDetails.getUserId());

        for(int i = 0;i<booksPageDto.getObjects().size();i++){

            booksPageDto.getObjects().get(i).setRoomUrl(checkBookService.findRoomImageUrlByRoomId(booksPageDto.getObjects().get(i).getRoomId()));
            booksPageDto.getObjects().get(i).setHostName(checkBookService.
                    findUserNameByUserId(booksPageDto.getObjects().get(i).getRoom().getUserId()));
        }
        for(int i = 0;i<booksPageDto.getObjects().size();i++){

            booksPageDto.getObjects().get(i).setUser(checkBookService.findUserByUserId(booksPageDto.getObjects().get(i).getUserId()));
        }

         model.addAttribute("booksPageDto",booksPageDto);

        return "/user-pages/user-book-list";
    }

    @GetMapping("/book/detail/{bookId}")
    public String checkBookListDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable("bookId") int bookId,
                                      Model model){

        CheckBookDto checkBookDto=checkBookService.findBookByBookId(bookId);
        checkBookDto.setUser(checkBookService.findUserByUserId(checkBookDto.getRoom().getUserId()));
        checkBookDto.setRoomUrls(checkBookService.findRoomImageUrlsByRoomId(checkBookService.findBookByBookId(bookId).getRoomId()));

//        결제dto셋팅
        checkBookDto.setPayment(checkBookService.findPaymentByBookId(bookId));
        checkBookDto.setUserUrl(checkBookService.findUserUrlByUserId(checkBookDto.getRoom().getUserId()));
        CheckBookHostDto hostDto= checkBookService.findHostByUserId(checkBookDto.getRoom().getUserId());

        int yearsOfExperience = roomDetailMapper.getHostDetail(checkBookDto.getRoomId()).getYearsOfExperience();


        model.addAttribute("book",checkBookDto);
        model.addAttribute("host",hostDto);
        model.addAttribute("yearsOfExperience",yearsOfExperience);


        return "/user-pages/user-book-detail";
    }
}
