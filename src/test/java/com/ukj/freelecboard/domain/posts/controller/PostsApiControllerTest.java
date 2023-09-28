package com.ukj.freelecboard.domain.posts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.repository.PostsRepository;
import com.ukj.freelecboard.domain.posts.dto.PostsSaveRequestDto;
import com.ukj.freelecboard.domain.posts.dto.PostsUpdateRequestDto;
import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //랜덤 포트 설정
class PostsApiControllerTest {

    @LocalServerPort private int port; // 이렇게 해야 무작위 포트를 자바 코드에서 사용 가능

    @Autowired private UserRepository userRepository;

    @Autowired private PostsRepository postsRepository;

    @Autowired private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void savePosts() throws Exception {
        //given
        User kim = userRepository.save(new User("kim", "kim@gmail.com", "7", null, Role.USER));
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .authorName("kim")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    void modifyPosts() throws Exception {
        //given
        User kim = userRepository.save(new User("kim", "kim@gmail.com", "7", null, Role.USER));
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author(kim)
                .build()
        );

        Long updateId = savedPosts.getId();
        String expectedTitle = "title!!";
        String expectedContent = "content!!";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    void deletePosts() throws Exception {
        //given
        User kim = userRepository.save(new User("kim", "kim@gmail.com", "7", null, Role.USER));
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author(kim)
                .build()
        );

        Long deleteId = savedPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + deleteId;

        //when
        mvc.perform(delete(url)).andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }
}