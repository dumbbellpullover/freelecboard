package com.ukj.freelecboard;

import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.service.UserService;
import com.ukj.freelecboard.web.dto.user.UserSaveRequestDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        public InitPostsService(UserService userService) {
            this.userService = userService;
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
        }
    }
}
