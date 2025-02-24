package com.minbak.web.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportStatusUpdate {

    private Integer reportId;  // 보고서 ID
    private String status;     // 변경할 상태
}
