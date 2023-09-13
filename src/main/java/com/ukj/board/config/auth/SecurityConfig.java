package com.ukj.board.config.auth;

import com.ukj.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정 활성화
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //h2-console 화면 사용 위한 옵션 disable 1
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                ) //h2-console 화면 사용 위한 옵션 disable 2
                .authorizeHttpRequests(matcher -> {
                    matcher.requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll(); //[permitAll() -> 전체 열람 권한]
                    matcher.requestMatchers("/api/v1/**").hasRole(Role.USER.name()); //USER 권한만 가능한 URL
                    matcher.anyRequest().authenticated(); //설정값 이외 나머지 URL [authenticated() -> 인증된 사용자만 허용]
                }) //권한 관리 대상 지정 옵션
                .logout(logout -> logout.logoutSuccessUrl("/")) //로그아웃 성공 시, 이동 위치
                .oauth2Login(login ->
                        login.userInfoEndpoint(endpoint -> //[userInfoEndpoint() -> 로그인 성공 후, 사용자 정보 가져올 때 설정]
                            endpoint.userService(customOAuth2UserService) //[userService() -> 소셜 로그인 성공 시 후속 조치의 UserService 구현체]
                        )
                ) //로그인 기능 설정
                .build();
    }
}
