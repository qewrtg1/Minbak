package com.minbak.web.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    @Autowired
    private BooksMapper booksMapper;

    public BooksPageDto getBooks(Integer page, Integer size) {
        int offset = (page -1) * size;
        List<BooksDto> books = booksMapper.selectBooksWithUser(size,offset);
        int totalElements = booksMapper.countTotalBooks();
        return new BooksPageDto(page, size, totalElements, books);
    }

    public void insertBook(BooksDto booksDto) {
        booksMapper.insertBook(booksDto);
    }

    public BooksDto selectBookById(Integer bookId) {
        return booksMapper.selectBookById(bookId);
    }

    public void updateBook(BooksDto booksDto) {
        booksMapper.updateBook(booksDto);
    }

    public void deleteBook(Integer bookId) {
        booksMapper.deleteBook(bookId);
    }
}
