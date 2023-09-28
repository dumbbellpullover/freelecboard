package com.ukj.freelecboard.domain.posts.controller;

import com.ukj.freelecboard.domain.comments.service.CommentsService;
import com.ukj.freelecboard.domain.posts.service.PostsService;
import com.ukj.freelecboard.domain.comments.dto.CommentsSaveRequestDto;
import com.ukj.freelecboard.domain.posts.dto.PostsListResponseDto;
import com.ukj.freelecboard.domain.posts.dto.PostsSaveRequestDto;
import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.domain.user.dto.UserSaveRequestDto;
import com.ukj.freelecboard.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@Slf4j
@Transactional
@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostsControllerTest {

    @Autowired TestRestTemplate restTemplate;
    @Autowired UserService userService;
    @Autowired CommentsService commentsService;
    @Autowired PostsService postsService;

    @Test
    void mainPageTest() throws Exception {
        //when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("게시판");
    }

    @AfterEach
    public void clean() {
        postsService.deleteAll();
    }

    @Test
    void commentsCount() throws Exception {
        //given
        userService.save(UserSaveRequestDto.builder()
                .password1("7")
                .role(Role.USER)
                .email("kim@gmail.com")
                .username("kim")
                .build());

        Long savedId = postsService.save(
                PostsSaveRequestDto.builder()
                .title("gg")
                .authorName("kim")
                .content("jj")
                .build());

        commentsService.save(savedId, CommentsSaveRequestDto.builder()
                .authorName("kim")
                .content("aa")
                .build()
        );

        commentsService.save(savedId, CommentsSaveRequestDto.builder()
                .authorName("kim")
                .content("aa2")
                .build()
        );

        //when
        List<PostsListResponseDto> contents = postsService.findPagingList(0).getContent();


        //then
        assertThat(contents.get(0).getCommentsSize()).isEqualTo(2);
    }
}