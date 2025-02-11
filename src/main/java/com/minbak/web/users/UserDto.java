package com.minbak.web.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    private Integer userId;               // 사용자 고유 ID (auto-increment)

    @NotBlank(message = "사용자 이름은 필수 항목입니다.")  // 이름은 비어있을 수 없음
    private String name;              // 사용자 이름

    @NotBlank(message = "이메일은 필수 항목입니다.")  // 이메일은 비어있을 수 없음
    @Email(message = "유효한 이메일 주소를 입력하세요.")  // 이메일 형식 검증
    private String email;             // 이메일

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678")  // 전화번호 형식 검증
    private String phoneNumber;       // 전화번호

    @NotBlank(message = "비밀번호는 필수 항목입니다.")  // 비밀번호는 비어있을 수 없음
    private String password;          // 비밀번호

    private boolean enabled = true;   // 계정 활성화 여부 (기본값 true)

    private LocalDateTime createdAt;  // 계정 생성일

    private LocalDateTime updatedAt;  // 계정 수정일

}
