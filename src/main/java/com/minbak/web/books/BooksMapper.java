package com.minbak.web.books;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BooksMapper {
    void insertBook(BooksDto booksDto);
    BooksDto selectBookById(Integer bookId);
    List<BooksDto> selectBooksByPage(@Param("size") int size, @Param("offset") int offset);
    int countTotalBooks();
    void updateBook(BooksDto booksDto);
    void deleteBook(Integer bookId);
}
