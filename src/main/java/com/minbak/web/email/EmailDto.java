package com.minbak.web.email;

import lombok.Data;

@Data
public class EmailDto {
    private String address;
    private String title;
    private String message;
}
