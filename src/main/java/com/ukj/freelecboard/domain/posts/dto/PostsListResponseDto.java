package com.ukj.freelecboard.domain.posts.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ukj.freelecboard.domain.posts.Posts;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {

    private final Long id;
    private final String title;
    private final String authorName;
    private final LocalDateTime createdDate;
    private final int commentsSize;

    @QueryProjection
    public PostsListResponseDto(Long id, String title, String authorName, LocalDateTime createdDate, int commentsSize) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.createdDate = createdDate;
        this.commentsSize = commentsSize;
    }

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.authorName = entity.getAuthor().getUsername();
        this.createdDate = entity.getCreatedDate();
        this.commentsSize = entity.getCommentsSize();
    }
}
