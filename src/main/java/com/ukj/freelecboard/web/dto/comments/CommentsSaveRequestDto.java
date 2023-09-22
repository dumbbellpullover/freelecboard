package com.ukj.freelecboard.web.dto.comments;

import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.domain.posts.Posts;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsSaveRequestDto {

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private String author;
    private Posts posts;

    @Builder
    public CommentsSaveRequestDto(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public Comments toEntity() {
        return Comments.builder()
                .content(content)
                .author(author)
                .posts(posts)
                .build();
    }
}
