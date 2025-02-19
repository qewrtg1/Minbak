package com.minbak.web.books;

import com.minbak.web.board.posts.BoardPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    private BooksMapper booksMapper;

    public BooksPageDto getBooks(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<BooksDto> books = booksMapper.selectBooksByPage(size,offset);
        int totalElements = booksMapper.countTotalBooks();
        return new BooksPageDto(page, size, totalElements, books);
    }

    public int countTotalBooks(){
        return booksMapper.countTotalBooks();
    }

    public BooksPageDto searchBooks(String searchType, String keyword, String dateType,
                                    LocalDate startDate, LocalDate endDate, String statusFilter,
                                    Integer page, Integer size) {
        int offset = (page - 1) * size;
        // 🔹 검색 조건이 없을 때 전체 리스트 조회 (status 또는 date만 있어도 검색 수행)
        boolean isEmptySearch = (searchType == null || searchType.isEmpty()) &&
                (keyword == null || keyword.isEmpty());

        if (isEmptySearch && (statusFilter == null || statusFilter.isEmpty()) && (dateType == null || dateType.isEmpty())) {
            return getBooks(page, size); // 전체 예약 리스트 반환
        }
        List<BooksDto> books = booksMapper.searchBooks(searchType, keyword, dateType, startDate, endDate, statusFilter, size, offset);
        int totalElements = booksMapper.countSearchedBooks(searchType, keyword, dateType, startDate, endDate, statusFilter);
        return new BooksPageDto(page, size, totalElements, books);
    }

    public void createBook(BooksDto booksDto) {
        booksMapper.createBook(booksDto);
    }

    public BooksDto selectBookById(Integer bookId) {
        return booksMapper.selectBookById(bookId);
    }

    public void editBook(BooksDto booksDto) {
        booksMapper.editBook(booksDto);
    }

    public void deleteBook(Integer bookId) {
        booksMapper.deleteBook(bookId);
    }
}
