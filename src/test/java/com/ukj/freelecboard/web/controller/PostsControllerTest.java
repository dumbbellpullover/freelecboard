package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.service.CommentsService;
import com.ukj.freelecboard.service.PostsService;
import com.ukj.freelecboard.web.dto.comments.CommentsSaveRequestDto;
import com.ukj.freelecboard.web.dto.posts.PostsListResponseDto;
import com.ukj.freelecboard.web.dto.posts.PostsSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
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
    @Autowired CommentsService commentsService;
    @Autowired PostsService postsService;

    @Test
    void mainPageTest() throws Exception {
        //when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("게시판");
    }

    @Test
    void commentsCount() throws Exception {
        //given
        Long savedId = postsService.save(
                PostsSaveRequestDto.builder()
                .title("gg")
                .author("kk")
                .content("jj")
                .build()
        );

        commentsService.save(savedId, CommentsSaveRequestDto.builder()
                .author("dd")
                .content("aa")
                .build()
        );

        commentsService.save(savedId, CommentsSaveRequestDto.builder()
                .author("dd2")
                .content("aa2")
                .build()
        );

        //when
        List<PostsListResponseDto> contents = postsService.findPagingList(0).getContent();


        //then
        assertThat(contents.get(0).getCommentsSize()).isEqualTo(2);
    }
}