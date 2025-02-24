package com.minbak.web.rooms;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomsDto {
    private int room_id;              // 숙소 고유 ID
    private String name;
    private String content;      // 숙소 설명
    private String address;      // 숙소 주소
    private double price;             // 숙소 가격
    private String use_guide;    // 숙소 이용 안내
    private double latitude;          // 숙소 위도 y
    private double longitude;         // 숙소 경도 x
    private Integer user_id;
    private String user_email;

    @Override
    public String toString() {
        return "RoomsDto{" +
                "user_email='" + user_email + '\'' +
                '}';
    }


//    usersDto 받아올 예정









//    reviewDto 받아올 예정











}
