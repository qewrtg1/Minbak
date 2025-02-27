package com.minbak.web.dash_board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRoomCountDto {
    private String optionName;  // 옵션 이름 (예: 에어컨, 와이파이 등)
    private int roomCount;      // 해당 옵션을 가진 숙소 개수
}
