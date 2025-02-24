package com.minbak.web.main_page.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainReviewDto {
    private String userName;
    private String content;
    private int score;
}