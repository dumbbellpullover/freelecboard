package com.ukj.freelecboard.service;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.PostsRepository;
import com.ukj.freelecboard.web.dto.comments.CommentsSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class CommentsServiceTest {

    @Autowired PostsService postsService;
    @Autowired CommentsService commentsService;
    @Autowired PostsRepository postsRepository;

    @AfterEach
    void after() {
        postsService.deleteAll();
    }

    @Test
    void save() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder().title("title1").author("oo1").content("content1").build());
        CommentsSaveRequestDto comments1 = CommentsSaveRequestDto.builder().content("reply1").author("rr1").build();

        log.info("comments.id = {}", savedPosts.getId());
        //when
        Long savedComments = commentsService.save(savedPosts.getId(), comments1);
        savedPosts.getComments().add(comments1.toEntity());
        //then
        assertThat(savedPosts.getComments().size()).isEqualTo(1);

    }
}