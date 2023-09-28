package com.ukj.freelecboard.domain.posts.dto;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.comments.dto.CommentsResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<CommentsResponseDto> comments;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.authorName = entity.getAuthor().getUsername();
        this.createdDate = entity.getCreatedDate();
        this.lastModifiedDate = entity.getLastModifiedDate();

        this.comments = entity.getComments().stream()
                .map(c -> new CommentsResponseDto(c))
                .toList();
    }
}
