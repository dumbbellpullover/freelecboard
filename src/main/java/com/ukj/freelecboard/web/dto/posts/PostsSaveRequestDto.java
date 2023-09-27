package com.ukj.freelecboard.web.dto.posts;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 200)
    private String title;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private String authorName;

    private User author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String authorName) {
        this.title = title;
        this.content = content;
        this.authorName = authorName;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
