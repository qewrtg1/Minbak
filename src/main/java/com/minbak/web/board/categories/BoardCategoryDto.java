package com.minbak.web.board.categories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCategoryDto {

    private Integer id;      // 고유 ID
    private String name;     // 카테고리 이름
    private Integer Order;   // 카테고리 순서

}
