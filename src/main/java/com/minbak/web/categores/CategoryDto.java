package com.minbak.web.categores;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Integer categoryId; // 데이터베이스의 category_id 컬럼과 매핑
    private String name;        // 카테고리 이름
    private int categoryOrder;
}
