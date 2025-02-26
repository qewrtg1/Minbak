package com.minbak.web.books;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksUserController {

    @Autowired
    private final BooksService booksService;

    @GetMapping("/create")
    public String create() {
        return "books/guest/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute BooksDto booksDto) {
        booksService.createBook(booksDto);
        return "books/guest/payment" + booksDto.getBookId();
    }

    @GetMapping("/payment/{bookId}")
    public String editBook(@PathVariable("bookId") Integer bookId, Model model) {
        BooksDto dto = booksService.selectBookById(bookId);
        model.addAttribute("books", dto);
        return "books/guest/payment";
    }

    @PostMapping("/edit/{bookId}")
    public String editBook(BooksDto booksDto, Model model) {
        booksService.editBook(booksDto);
        BooksDto dto = booksService.selectBookById(booksDto.getBookId());
        model.addAttribute("books", dto);
        return "books/guest/payment";
    }
}
