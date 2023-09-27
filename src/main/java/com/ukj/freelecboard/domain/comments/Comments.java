package com.ukj.freelecboard.domain.comments;

import com.ukj.freelecboard.domain.BaseTimeEntity;
import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comments extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @Builder
    public Comments(Posts posts, User author, String content) {
        this.posts = posts;
        this.author = author;
        this.content = content;
        addComments(posts);
    }

    private void addComments(Posts posts) {
        posts.getComments().add(this);
        posts.increaseCommentsSize();
    }


}
