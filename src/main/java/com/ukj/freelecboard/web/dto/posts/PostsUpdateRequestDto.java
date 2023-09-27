package com.ukj.freelecboard.web.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String authorName;
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String authorName, String title, String content) {
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }
}
