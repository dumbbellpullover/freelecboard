package com.ukj.freelecboard.domain.comments.repository;

import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.domain.comments.dto.CommentsSaveRequestDto;
import com.ukj.freelecboard.domain.comments.repository.CommentsRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentsRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Test
    void save() throws Exception {
        //given
        User kim = userRepository.save(new User("kim", "kim@gmail.com", "7", null, Role.USER));
        Posts kimPosts = postsRepository.save(new Posts(kim, "ㅎㅇ", "hi"));

        String content = "test content";

        commentsRepository.save(new Comments(kimPosts, kim, content));

        //when
        List<Comments> comments = commentsRepository.findAll();

        //then
        assertThat(comments.get(0).getContent()).isEqualTo(content);
    }
}