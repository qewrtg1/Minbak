package com.minbak.web.board.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCategoryDto {

    private Integer id;      // 고유 ID

    @NotBlank(message = "카테고리 이름은 필수입니다.") // 카테고리 이름이 비어있지 않도록 검사
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "카테고리 이름에 특수문자는 사용할 수 없습니다.") // 특수문자 금지
    private String name;     // 카테고리 이름
    private Integer Order;   // 카테고리 순서

}
