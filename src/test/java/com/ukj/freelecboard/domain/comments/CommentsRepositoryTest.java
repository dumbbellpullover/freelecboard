package com.ukj.freelecboard.domain.comments;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.PostsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentsRepositoryTest {

    @Autowired CommentsRepository commentsRepository;

    @AfterEach
    public void clean() {
        commentsRepository.deleteAll();
    }

    @Test
    void save() throws Exception {
        //given
        String content = "test content";

        commentsRepository.save(Comments.builder()
                .content(content)
                .author("me")
                .build()
        );

        //when
        List<Comments> comments = commentsRepository.findAll();

        //then
        assertThat(comments.get(0).getContent()).isEqualTo(content);
    }
}