package com.minbak.web.books;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BooksController {

    @Autowired
    private final BooksService booksService;

    @GetMapping
    public String searchBooks(@RequestParam(name="page", defaultValue = "1") int page,
                              @RequestParam(name="size", defaultValue = "10") int size,
                              @RequestParam(name="searchType", required = false) String searchType,
                              @RequestParam(name="keyword", required = false) String keyword,
                              @RequestParam(name="dateType", required = false) String dateType,
                              @RequestParam(name="startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam(name="endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                              @RequestParam(name="statusFilter", required = false) String statusFilter,
                              Model model) {

        model.addAttribute("booksPage",booksService.searchBooks(searchType, keyword, dateType, startDate, endDate, statusFilter, page, size));

        return "books/list";
    }

    @GetMapping("/create")
    public String create() {
        return "books/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute BooksDto booksDto) {
        booksService.createBook(booksDto);
        return "redirect:/admin/books";
    }

    @GetMapping("/{bookId}")
    public String selectBookById(@PathVariable("bookId") Integer bookId, Model model) {
        BooksDto booksDto = booksService.selectBookById(bookId);
        model.addAttribute("books", booksDto);
        return "books/detail";
    }

    @GetMapping("/edit/{bookId}")
    public String editBook(@PathVariable("bookId") Integer bookId, Model model) {
        BooksDto dto = booksService.selectBookById(bookId);
        model.addAttribute("books", dto);
        return "books/edit";
    }

    @PostMapping("/edit/{bookId}")
    public String editBook(BooksDto booksDto, Model model) {
        booksService.editBook(booksDto);
        BooksDto dto = booksService.selectBookById(booksDto.getBookId());
        model.addAttribute("books", dto);
        return "books/detail";
    }

    @GetMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Integer bookId) {
        booksService.deleteBook(bookId);
        return "redirect:/admin/books";
    }
}
