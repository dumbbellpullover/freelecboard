package com.ukj.freelecboard.web.dto.comments;

import com.ukj.freelecboard.domain.comments.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsResponseDto {

    private Long comment_id;
    private String content;
    private String authorName;
    private LocalDateTime lastModifiedDate;

    /* DTO -> Entity */
    public CommentsResponseDto(Comments entity) {
        this.comment_id = entity.getId();
        this.content = entity.getContent();
        this.authorName = entity.getAuthor().getUsername();
        this.lastModifiedDate = entity.getLastModifiedDate();
    }
}
