package com.ukj.freelecboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig -> CustomOAuth2UserService
 * CustomOAuth2UserService -> PasswordEncoder
 * PasswordEncoder 의 Bean 은 SecurityConfig 내부에서 등록
 * 결국 CustomOAuth2UserService 는 SecurityConfig 을 참조하게 되면서 순환 참조 발생
 *
 * PasswordEncoderConfig 새로 만들어 순환 참조 해결
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
