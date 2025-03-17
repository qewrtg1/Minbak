package com.minbak.web.categories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDto {
    private int optionId;        // 옵션 ID
    private String name;         // 옵션 이름
    private String optionsCategory; // 옵션이 속한 카테고리
}
