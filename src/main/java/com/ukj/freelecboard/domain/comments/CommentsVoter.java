package com.ukj.freelecboard.domain.comments;

import com.ukj.freelecboard.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommentsVoter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User voter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comments_id")
    private Comments comments;

    public static CommentsVoter createPostsVoter(Comments comments, User voter) {
        return CommentsVoter.builder()
                .comments(comments)
                .user(voter)
                .build();
    }

    @Builder
    private CommentsVoter(Comments comments, User user) {
        this.comments = comments;
        this.voter = user;
    }
}
