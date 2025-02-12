package com.minbak.web.board.posts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardPostDto {

    private Integer id;     // 게시글 ID

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 50, message = "제목은 50자 이하로 작성해야 합니다.")
    private String title;   // 게시글 제목

    @NotBlank(message = "내용은 필수입니다.")
    private String content;     // 게시글 내용

    @NotBlank(message = "작성자는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "작성자는 특수문자나 공백을 포함할 수 없습니다.")
    private String author;      // 작성자

    @NotBlank(message = "말머리는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "말머리는 특수문자나 공백을 포함할 수 없습니다.")
    private String subject;     // 말머리 (선택 사항)

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private int categoryId;     // 카테고리 ID (외래 키)

    private String categoryName; // 카테고리 이름 (조인 결과)
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일

}