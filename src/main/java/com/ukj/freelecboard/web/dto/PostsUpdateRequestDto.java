package com.ukj.freelecboard.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String author;
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
