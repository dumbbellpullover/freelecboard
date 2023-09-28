package com.ukj.freelecboard.domain.posts.repository;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.repository.PostsRepository;
import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void clean() {
        postsRepository.deleteAll();
    }

    @Test
    void savePosts() throws Exception {
        //given
        User user = userRepository.save(new User("kim", "kim@gmail.com", "7", null, Role.USER));
        String title = "test title";
        String content = "test content";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(user)
                .build()
        );

        //when
        List<Posts> results = postsRepository.findAll();

        //then
        Posts result = results.get(0);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getAuthor().getUsername()).isEqualTo("kim");
    }

    @Test
    void baseTimeEntityTest() throws Exception {
        //given
        User user = userRepository.save(new User("kim", "kim@gmail.com", "7", null, Role.USER));

        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("t")
                .content("c")
                .author(user)
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