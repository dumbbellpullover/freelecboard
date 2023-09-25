package com.ukj.freelecboard.config.auth;

import com.ukj.freelecboard.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //Spring Security 설정 활성화
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        //[permitAll() -> 전체 열람 권한]
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/posts")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user/new")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user/login")).permitAll()
                        //USER 권한만 가능한 URL
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).hasRole(Role.USER.name())
                        //설정값 이외 나머지 URL [authenticated() -> 인증된 사용자만 허용]
                        .anyRequest().authenticated()
                ) //권한 관리 대상 지정 옵션
                .csrf(csrf -> csrf.disable())
//                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))) //h2-console 화면 사용 위한 옵션 disable 1
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) //h2-console 화면 사용 위한 옵션 disable 2
                .oauth2Login(login -> login
                                .loginPage("/user/login") //loginPage
                                .userInfoEndpoint(endpoint -> //[userInfoEndpoint() -> 로그인 성공 후, 사용자 정보 가져올 때 설정]
                                endpoint.userService(customOAuth2UserService) //[userService() -> 소셜 로그인 성공 시 후속 조치의 UserService 구현체]
                        ).defaultSuccessUrl("/", true) //로그인 성공 후, 이동 위치 [true -> 강제 이동]
                ) //로그인 기능 설정
                .formLogin(formLogin -> formLogin
                                .loginPage("/user/login")
                                .defaultSuccessUrl("/")) //폼 로그인
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) //로그아웃 가능하게 함
                        .logoutSuccessUrl("/") //로그아웃 성공 시, 이동 위치
                        .invalidateHttpSession(true)) //세션 삭제
                .build();
    }

//    @Bean / 등록하면 폼으로 로그인 후에 세션을 받을 수 있음.
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}

