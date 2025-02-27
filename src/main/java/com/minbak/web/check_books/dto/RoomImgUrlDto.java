package com.minbak.web.check_books.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomImgUrlDto {

    private Integer imageId;
    private Integer roomId;
    private String fileUrl;
}
