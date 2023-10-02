package com.ukj.freelecboard.domain.comments.dto;

import com.ukj.freelecboard.domain.comments.Comments;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentsResponseDto {

    private Long commentId;
    private String content;
    private String authorName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private int voteCount;

    /* DTO -> Entity */
    public CommentsResponseDto(Comments entity) {
        this.commentId = entity.getId();
        this.content = entity.getContent();
        this.authorName = entity.getAuthor().getUsername();
        this.createdDate = entity.getCreatedDate();
        this.lastModifiedDate = entity.getLastModifiedDate();
        this.voteCount = entity.getVoteCount();
    }
}
