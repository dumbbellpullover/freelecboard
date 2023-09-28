package com.ukj.freelecboard.domain.comments.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentsUpdateRequestDto {
    @NotEmpty(message = "내용은 필수 항목입니다.")
    String content;

    @Builder
    public CommentsUpdateRequestDto(String content) {
        this.content = content;
    }
}
