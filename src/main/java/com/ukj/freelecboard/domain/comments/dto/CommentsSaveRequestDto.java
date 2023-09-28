package com.ukj.freelecboard.domain.comments.dto;

import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
public class CommentsSaveRequestDto {

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private String authorName;
    private User author;
    private Posts posts;

    @Builder
    public CommentsSaveRequestDto(String authorName, String content) {
        this.content = content;
        this.authorName = authorName;
    }

    public Comments toEntity() {
        return Comments.builder()
                .content(content)
                .author(author)
                .posts(posts)
                .build();
    }
}
