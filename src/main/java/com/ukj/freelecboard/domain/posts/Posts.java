package com.ukj.freelecboard.domain.posts;

import com.ukj.freelecboard.domain.BaseTimeEntity;
import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
    private int commentsCount;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    List<Comments> comments = new ArrayList<>();

    @Builder
    public Posts(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.commentsCount = 0;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseCommentsSize() {
        this.commentsCount++;
    }
}
