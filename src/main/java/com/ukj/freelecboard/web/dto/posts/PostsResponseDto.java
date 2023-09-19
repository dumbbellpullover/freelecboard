package com.ukj.freelecboard.web.dto.posts;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.web.dto.comments.CommentsResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime lastModifiedDate;
    private List<CommentsResponseDto> comments = new ArrayList<>();

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.lastModifiedDate = entity.getLastModifiedDate();

        this.comments = entity.getComments().stream()
                .map(c -> new CommentsResponseDto(c))
                .toList();
    }
}
