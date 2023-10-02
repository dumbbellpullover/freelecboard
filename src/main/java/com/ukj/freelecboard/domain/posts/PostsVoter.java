package com.ukj.freelecboard.domain.posts;

import com.ukj.freelecboard.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostsVoter {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User voter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    //==생성 메서드==//
    public static PostsVoter createPostsVoter(Posts posts, User voter) {
        return PostsVoter.builder()
                .posts(posts)
                .user(voter)
                .build();
    }

    @Builder
    private PostsVoter(Posts posts, User user) {
        this.posts = posts;
        this.voter = user;
    }
}
