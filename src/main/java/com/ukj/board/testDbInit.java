package com.ukj.board;

import com.ukj.board.domain.posts.Posts;
import com.ukj.board.domain.posts.PostsRepository;
import com.ukj.board.service.PostsService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class testDbInit {

    private final InitPostsService initPostsService;

    @PostConstruct
    public void init() {
        initPostsService.init();
    }

    @Component
    static class InitPostsService {
        @PersistenceContext
        EntityManager em;
        @Transactional
        public void init() {
            for (int i = 1; i <= 50; i++) {
                em.persist(
                        Posts.builder()
                                .title("title" + i)
                                .content("content" + i)
                                .author("ㅇㅇ" + i)
                                .build()
                );
            }
        }
    }
}
