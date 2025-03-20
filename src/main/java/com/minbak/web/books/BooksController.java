package com.minbak.web.books;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @PostMapping("/updateStatus/{bookId}")
    public String updateStatus(@PathVariable("bookId") Integer bookId, @RequestParam String status, Model model) {
        BooksDto booksDto = booksService.selectBookById(bookId);
        booksDto.setStatus(status);
        booksService.editBook(booksDto);
        model.addAttribute("books", booksDto);
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
        System.out.println("roomId: " + dto.getRoomId());
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

    @GetMapping("/monthly")
    public String findMonthlyBooks(@RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int year,
                                   @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getMonthValue()}") int month,
                                   Model model) {
//        이전달/다음달 페이징
        LocalDate currentMonth = LocalDate.of(year, month, 1);
        LocalDate prevMonth = currentMonth.minusMonths(1);
        LocalDate nextMonth = currentMonth.plusMonths(1);

        Map<String, Map<LocalDate, List<String>>> statusOfRoom = booksService.findMonthlyBooks(year, month);

//        이번 달 모든 날짜 리스트 생성
        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        List<LocalDate> datesInMonth = IntStream.rangeClosed(1, daysInMonth)
                .mapToObj(day -> LocalDate.of(year, month, day))
                .collect(Collectors.toList());

        model.addAttribute("datesInMonth", datesInMonth);
        model.addAttribute("statusOfRoom", statusOfRoom);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", prevMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());
        return "books/monthly";
    }

    @GetMapping("/wait")
    public String getWaitingBooks(Model model) {
        List<BooksDto> waitingBooks = booksService.getWaitingBooks();
        model.addAttribute("waitingBooks", waitingBooks);
        return "books/wait";
    }

    @GetMapping("/paidAndCheckIn")
    public String paidAndCheckIn(Model model) {
        List<BooksDto> paidAndCheckIn = booksService.paidAndCheckIn();
        model.addAttribute("paidAndCheckIn", paidAndCheckIn);
        return "books/paidAndCheckIn";
    }
}
