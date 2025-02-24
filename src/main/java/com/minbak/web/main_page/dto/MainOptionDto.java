package com.minbak.web.main_page.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainOptionDto {
    private int optionId;
    private String name;
    private String category; // 옵션 카테고리 (새로 추가된 필드)
}
