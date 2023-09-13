package com.ukj.freelecboard.web;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.PostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@Slf4j
@Transactional
@SpringBootTest(webEnvironment = RANDOM_PORT)
class IndexControllerTest {

    @Autowired TestRestTemplate restTemplate;
    @Autowired PostsRepository postsRepository;

    @BeforeEach
    void before() {
        for (int i = 1; i <= 100; i++) {
            postsRepository.save(new Posts("title" + i, "content" + i, "ㅇㅇ" + i));
        }
    }

    @Test
    void mainPageTest() throws Exception {
        //when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("게시판");
    }

    @Test
    void postsListPage() throws Exception {
        String body = this.restTemplate.getForObject("/posts", String.class);

        assertThat(body).contains("100");
    }

    @Test
    void postsNewFormPage() throws Exception {
        String body = this.restTemplate.getForObject("/posts/new", String.class);

        assertThat(body).contains("<form role=\"form\" action=\"/posts/new\"");
    }
}