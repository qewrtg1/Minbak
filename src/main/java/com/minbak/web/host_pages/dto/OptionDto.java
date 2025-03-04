package com.minbak.web.host_pages.dto;

import lombok.Data;

@Data
public class OptionDto {
    private Integer optionId;      // 옵션 ID (room_options 테이블의 option_id)
    private String name;           // 옵션 이름
    private String optionsCategory;       // 옵션 카테고리 (예: 필수, 특징, 위치, 안전 등)


}
