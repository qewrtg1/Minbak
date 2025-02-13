package com.minbak.web.books;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BooksController {

    @Autowired
    private final BooksService booksService;

    @GetMapping
    public String booksList(@RequestParam(name="page", defaultValue = "1") int page,
                            @RequestParam(name="size", defaultValue = "10") int size,
                            Model model){
        BooksPageDto booksPage = booksService.getBooks(page, size);
        model.addAttribute("booksPage" , booksPage);
        return "board/books/list";
    }

    @GetMapping("/save")
    public String save() {
        return "board/books/save";
    }

    @PostMapping("/save")
    public String save(BooksDto booksDto) {
        booksService.insertBook(booksDto);
        return "redirect:/list";
    }

    @GetMapping("/{bookId}")
    public String selectBookById(@PathVariable("bookId") Integer bookId, Model model) {
        BooksDto booksDto = booksService.selectBookById(bookId);
        model.addAttribute("books", booksDto);
        System.out.println("booksDto = " + booksDto);
        return "board/books/detail";
    }

    @GetMapping("/update/{bookId}")
    public String updateBook(@PathVariable("bookId") Integer bookId, Model model) {
        BooksDto dto = booksService.selectBookById(bookId);
        model.addAttribute("dto = " + dto);
        return "board/books/update";
    }

    @PostMapping("/update/{bookId}")
    public String updateBook(BooksDto booksDto, Model model) {
        booksService.updateBook(booksDto);
        BooksDto dto = booksService.selectBookById(booksDto.getBookId());
        model.addAttribute("dto = " + dto);
        return "board/books/detail";
    }

    @GetMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Integer bookId) {
        booksService.deleteBook(bookId);
        return "redirect:/list";
    }
}
