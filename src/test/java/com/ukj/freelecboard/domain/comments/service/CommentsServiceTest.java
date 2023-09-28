package com.ukj.freelecboard.domain.comments.service;

import com.ukj.freelecboard.domain.comments.service.CommentsService;
import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.repository.PostsRepository;
import com.ukj.freelecboard.domain.posts.service.PostsService;
import com.ukj.freelecboard.domain.comments.dto.CommentsSaveRequestDto;
import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class CommentsServiceTest {

    @Autowired PostsService postsService;
    @Autowired CommentsService commentsService;
    @Autowired PostsRepository postsRepository;
    @Autowired UserRepository userRepository;

    @Test
    void save() throws Exception {
        //given
        User kim = userRepository.save(new User("kim", "kim@gmail.com", "7", null, Role.USER));
        Posts kimPosts = postsRepository.save(Posts.builder().title("title1").author(kim).content("content1").build());

        String content = "reply1";
        CommentsSaveRequestDto kimComments = CommentsSaveRequestDto.builder().content("reply1").authorName("kim").build();

        log.info("comments.id = {}", kimPosts.getId());
        //when
        Long savedComments = commentsService.save(kimPosts.getId(), kimComments);

        //then
        assertThat(commentsService.findById(savedComments).getContent()).isEqualTo(content);

    }
}