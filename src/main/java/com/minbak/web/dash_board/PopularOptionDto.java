package com.minbak.web.dash_board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopularOptionDto {
    private int optionId;
    private String optionName;
    private int usageCount;

}
