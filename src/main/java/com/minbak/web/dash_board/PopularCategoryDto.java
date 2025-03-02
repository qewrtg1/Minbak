package com.minbak.web.dash_board;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopularCategoryDto {
    private int categoryId;
    private String categoryName;
    private int bookingCount;

}
