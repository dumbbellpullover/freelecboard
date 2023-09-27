package com.ukj.freelecboard;

import com.ukj.freelecboard.domain.posts.Posts;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Profile("test")
//@Component
@RequiredArgsConstructor
public class testDbInit {

    private final InitPostsService initPostsService;

//    @PostConstruct
    public void init() {
        initPostsService.init();
    }

//    @Component
    static class InitPostsService {
        @PersistenceContext
        EntityManager em;
        @Transactional
        public void init() {
            for (int i = 1; i <= 10; i++) {
                em.persist(
                        Posts.builder()
                                .title("title" + i)
                                .content("content" + i)
//                                .author("ㅇㅇ" + i)
                                .build()
                );
            }
        }
    }
}
