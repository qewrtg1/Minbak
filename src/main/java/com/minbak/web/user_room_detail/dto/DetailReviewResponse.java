package com.minbak.web.user_room_detail.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetailReviewResponse {
    private int reviewId;
    private DetailUserResponse user;
    private int rating;
    private String content;
    private LocalDateTime date;
}
