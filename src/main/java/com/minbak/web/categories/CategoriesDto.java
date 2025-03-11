package com.minbak.web.categories;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesDto {
    private Integer categoryId; // 데이터베이스의 category_id 컬럼과 매핑

    @NotBlank(message = "카테고리 이름은 공백일 수 없습니다.")
    private String name;        // 카테고리 이름

    private int categoryOrder;
}
