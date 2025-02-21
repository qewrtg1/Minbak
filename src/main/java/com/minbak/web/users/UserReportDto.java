package com.minbak.web.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserReportDto {

    private Integer reportId;                // 신고 고유 ID
    private Integer reporterId;              // 신고한 유저의 ID
    private Integer reportedUserId;          // 신고 당한 유저의 ID
    private String reportReason;          // 신고 사유
    private LocalDateTime reportDate;     // 신고 접수 시간
    private String status;                // 신고 처리 상태
    private LocalDateTime processedAt;    // 신고 처리 완료 시간

    // 신고한 유저 이메일
    private String reporterEmail;

    // 신고 당한 유저 이메일
    private String reportedUserEmail;
}
