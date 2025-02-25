package com.minbak.web.books;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/apii/books")
public class BooksApiController {

    @Autowired
    private final BooksService booksService;

    @GetMapping
    public String getBooks(@RequestParam(name="page", defaultValue = "1") int page,
                           @RequestParam(name="size", defaultValue = "10") int size,
                           Model model) {
        model.addAttribute("booksPage", booksService.getBooks(page, size));
        return "books/hosting/reservations";
    }
}
