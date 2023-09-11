package com.ukj.board.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void clean() {
        postsRepository.deleteAll();
    }

    @Test
    void savePosts() throws Exception {
        //given
        String title = "test title";
        String content = "test content";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("me")
                .build()
        );

        //when
        List<Posts> results = postsRepository.findAll();

        //then
        Posts result = results.get(0);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getAuthor()).isEqualTo("me");
    }

    @Test
    void baseTimeEntityTest() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("t")
                .content("c")
                .author("a")
                .build());

        //when
        List<Posts> all = postsRepository.findAll();

        //then
        Posts posts = all.get(0);
        System.out.println(">>>>>>>>>> now=" + now);
        System.out.println(">>>>>>>>>> createDate=" + posts.getCreatedDate());
        System.out.println(">>>>>>>>>> lastModifiedDate=" + posts.getLastModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getLastModifiedDate()).isAfter(now);
    }
}