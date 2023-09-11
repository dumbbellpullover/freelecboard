package com.ukj.board.web.dto;

import com.ukj.board.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponsesDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponsesDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
