package com.minbak.web.roomoptions;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomOptionsDto {
    private int optionId;

    @NotBlank(message = "편의시설 이름을 입력해주세요.")
    @Size(max = 50, message = "편의시설 이름은 최대 50자까지 입력 가능합니다.")
    private String name;

    private int roomId;
    private String content;
    private String address;
    private int price;
    private List<String> amenities; // 편의시설 목록 추가

}