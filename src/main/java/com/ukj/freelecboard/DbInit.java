package com.ukj.freelecboard;

import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.domain.posts.Posts;
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
public class DbInit {

    private final InitPostsService initPostsService;

    @PostConstruct
    public void init() {
        initPostsService.init();
    }

    @Component
    static class InitPostsService {
        @PersistenceContext EntityManager em;
        @Transactional
        public void init() {
            for (int i = 1; i <= 300; i++) {
                em.persist(Posts.builder()
                                    .title("title" + i)
                                    .content("content" + i)
                                    .author("ㅇㅇ" + i)
                                    .build());
            }

            for (int i = 1; i <= 300; i++) {
                em.persist(
                        Comments.builder()
                                .content("content" + i)
                                .author("ㅇㅇ" + i)
                                .posts(em.find(Posts.class, i))
                                .build()
                );
            }
        }
    }
}
