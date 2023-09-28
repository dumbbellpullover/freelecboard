package com.ukj.freelecboard.domain.posts.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostsUpdateRequestDto {

    @NotEmpty(message = "제목은 필수 항목입니다.")
    private String title;

    @NotEmpty(message = "제목은 필수 항목입니다.")
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
