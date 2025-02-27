package com.minbak.web.rooms.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomsPageDto {
    private int page;
    private int size;
    private int totalPages;
    private int totalElements;
    private int pageGroupSize = 10;
    private int start;
    private int end;
    private boolean prev;
    private boolean next;
    private List<RoomsListDto> rooms;

    public RoomsPageDto(int page, int size, int totalElements, List<RoomsListDto> rooms) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;

        // 전체 페이지 계산
        this.totalPages = (int) Math.ceil((double) totalElements / size);

        // 페이지 그룹 범위 계산 (예: 10개씩 그룹)
        this.start = ((page - 1) / pageGroupSize) * pageGroupSize + 1;
        this.end = Math.min(start + pageGroupSize - 1, totalPages);

        // 이전 / 다음 버튼 조건 수정: 현재 페이지 기준으로 판단
        this.prev = page > 1;
        this.next = page < totalPages;

        this.rooms = rooms;
    }


}
