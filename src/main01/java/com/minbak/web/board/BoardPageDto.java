package com.minbak.web.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardPageDto<T> {

    private int currentPage;    // 현재 페이지 번호
    private int pageSize;       // 한 페이지당 게시글 수
    private int totalItems;     // 전체 게시글 수
    private int totalPages;     // 전체 페이지 수
    private int startPage;      // 페이지 네비게이션에서 시작 페이지
    private int endPage;        // 페이지 네비게이션에서 끝 페이지
    private boolean hasPrev;    // 이전 페이지가 있는지 여부
    private boolean hasNext;    // 다음 페이지가 있는지 여부
    private List<T> objects;    // 페이지에 받아올 객체 리스트 데이터

    //현재페이지, 페이지사이즈, 모든아이템수, 데이터리스트를 받아서 위 필드를 계산 후 주입
    public BoardPageDto(int currentPage, int pageSize, int totalItems, List<T> objects) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.objects = objects;

        // 전체 페이지 수 계산
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // halfRange는 페이지 네비게이션에서 표시할 범위를 데이터에 맞게 동적으로 설정
        int halfRange = 2;

        // 시작 페이지와 끝 페이지 계산 (예: 1, 2, 3, 4, 5 페이지 네비게이션)
        this.startPage = Math.max(1, currentPage - halfRange);
        this.endPage = Math.min(totalPages, currentPage + halfRange);

        // 이전, 다음 페이지 존재 여부
        this.hasPrev = currentPage > 1;
        this.hasNext = currentPage < totalPages;
    }

}
