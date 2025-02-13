package com.minbak.web.books;

import lombok.Data;

import java.util.List;

@Data
public class BooksPageDto {
    private int page;
    private int size;
    private int totalPages;
    private int totalElements;
    private int pageGroupSize = 10;
    private int start;
    private int end;
    private boolean prev;
    private boolean next;
    private List<BooksDto> books;


    public BooksPageDto(int page, int size, int totalElements, List<BooksDto> books) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;

//        전체 페이지
        this.totalPages = (int) Math.ceil((double) totalElements / size);

//        시작 페이지
        this.start = ((page -1) / pageGroupSize) * pageGroupSize + 1;

//        끝 페이지
        this.end = Math.min(start + pageGroupSize - 1 , totalPages);

//        이전 / 다음 버튼
        this.prev = start > 1;
        this.next = end < totalPages;

        this.books = books;
    }
}
