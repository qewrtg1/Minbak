package com.minbak.web.books;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BooksMapper {
    void createBook(BooksDto booksDto);
    BooksDto selectBookById(Integer bookId);
    List<BooksDto> selectBooksByPage(@Param("size") int size, @Param("offset") int offset);
    List<BooksDto> searchBooks(@Param("searchType") String searchType,
                               @Param("keyword") String keyword,
                               @Param("dateType") String dateType,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate,
                               @Param("statusFilter") String statusFilter,
                               @Param("size") int size,
                               @Param("offset") int offset);

    int countSearchedBooks(@Param("searchType") String searchType,
                           @Param("keyword") String keyword,
                           @Param("dateType") String dateType,
                           @Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate,
                           @Param("statusFilter") String statusFilter);
    int countTotalBooks();
    void editBook(BooksDto booksDto);
    void deleteBook(Integer bookId);
}
