package com.ukj.board;

import com.ukj.board.domain.posts.Posts;
import com.ukj.board.domain.posts.PostsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final PostsRepository postsRepository;

    @PostConstruct
    public void init() {
        postsRepository.save(new Posts("title1", "content1", "ㅇㅇ"));
        postsRepository.save(new Posts("title2", "content2", "ㅇㅇ"));
    }
}
