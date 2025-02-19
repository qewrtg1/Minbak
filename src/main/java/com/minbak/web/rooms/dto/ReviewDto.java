package com.minbak.web.rooms.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private int reviewId;
    private String reviewContent;
    private int reviewScore;
}
