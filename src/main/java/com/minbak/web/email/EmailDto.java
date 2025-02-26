package com.minbak.web.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    private String to;
    private String title;
    private String message;
}
