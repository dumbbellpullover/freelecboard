package com.ukj.freelecboard.web.dto.posts;

import com.ukj.freelecboard.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime lastModifiedDate;
    private int commentsSize;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.lastModifiedDate = entity.getLastModifiedDate();
        this.commentsSize = entity.getCommentsCount();
    }
}
