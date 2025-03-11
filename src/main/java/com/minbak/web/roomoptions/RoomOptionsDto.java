package com.minbak.web.roomoptions;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class RoomOptionsDto {
    private int optionId;

    @NotBlank(message = "편의시설 이름을 입력해주세요.")
    @Size(max = 50, message = "편의시설 이름은 최대 50자까지 입력 가능합니다.")
    private String name;
    @NotBlank(message = "카테고리는 필수입니다.")
    @Pattern(regexp = "필수|특징|위치|안전", message = "잘못된 카테고리 값입니다.")
    private String optionsCategory; // 추가된 옵션 카테고리 필드





    // 이하 dto는 테스트용임 //
    private int roomId;
    private String content;
    private String address;
    private int price;
    private String amenities;
    // 편의시설을 List로 변환
    public List<String> getAmenitiesList() {
        if (amenities != null && !amenities.isEmpty()) {
            return Arrays.asList(amenities.split(", "));  // 쉼표를 기준으로 나눔
        }
        return List.of(); // 빈 리스트 반환
    }

}