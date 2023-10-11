package com.ukj.freelecboard;

import com.ukj.freelecboard.domain.comments.dto.CommentsSaveRequestDto;
import com.ukj.freelecboard.domain.comments.service.CommentsService;
import com.ukj.freelecboard.domain.posts.dto.PostsSaveRequestDto;
import com.ukj.freelecboard.domain.posts.service.PostsService;
import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.service.UserService;
import com.ukj.freelecboard.domain.user.dto.UserSaveRequestDto;
import jakarta.annotation.PostConstruct;
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

        private final UserService userService;
        private final PostsService postsService;
        private final CommentsService commentsService;

        public InitPostsService(UserService userService, PostsService postsService, CommentsService commentsService) {
            this.userService = userService;
            this.postsService = postsService;
            this.commentsService = commentsService;
        }

        @Transactional
        public void init() {
            userService.save(UserSaveRequestDto.builder()
                    .username("kim")
                    .email("asd@gmail.com")
                    .password1("7")
                    .role(Role.USER)
                    .build());

            userService.save(UserSaveRequestDto.builder()
                    .username("park")
                    .email("qwe@gmail.com")
                    .password1("7")
                    .role(Role.USER)
                    .build());

            Long kimPostsId = postsService.save(PostsSaveRequestDto.builder()
                    .title("ㅎㅇ")
                    .content("ㅎㅇ")
                    .authorName("kim")
                    .build());

            commentsService.save(kimPostsId, CommentsSaveRequestDto.builder()
                    .authorName("park")
                    .content("hi")
                    .build());

            commentsService.save(kimPostsId, CommentsSaveRequestDto.builder()
                    .authorName("kim")
                    .content("ㅎㅇㅎㅇ")
                    .build());

            Long parkPostsId = postsService.save(PostsSaveRequestDto.builder()
                    .title("hello")
                    .content("hello")
                    .authorName("park")
                    .build());

            commentsService.save(parkPostsId, CommentsSaveRequestDto.builder()
                    .authorName("kim")
                    .content("하이")
                    .build());

            commentsService.save(parkPostsId, CommentsSaveRequestDto.builder()
                    .authorName("park")
                    .content("hellohello")
                    .build());


            for (int i = 3; i <= 25; i++) {
                postsService.save(PostsSaveRequestDto.builder()
                        .title("ㅎㅇ" + i)
                        .content("ㅎㅇ")
                        .authorName("kim")
                        .build());
            }
        }
    }
}
