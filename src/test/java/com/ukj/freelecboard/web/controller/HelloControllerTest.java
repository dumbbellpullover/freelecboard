package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * * @WebMvcTest(controllers = HelloController.class, excludeFilters = ...)
 * MockMvc 자동 주입 + controller 범위 제한
 *
 * 하지만, @Repository, @Service, @Component 스캔하지 않음
 * WebSecurityConfigurerAdapter, WebMvcConfigurer 및 @ControllerAdvice, @Controller 등을 읽음
 *
 * 에러 상황: SecurityConfig 는 읽었지만, SecurityConfig 생성 시 필요한 CustomOAuth2UserService 는 읽지 못함
 * 문제점: CustomOAuth2UserService 스캔하지 못해 주입이 안되서 bean 에러
 * 해결: excludeFilter 를 통해 현재 필요하지 않은 SecurityConfig 를 스캔하지 않음 + 각 테스트에 @WithMockUser(roles = "USER") 추가
 *
 * 추가 에러 상황: JPA metamodel must not be empty
 * Auditing 을 사용 중 -> Auditing 사용 시 최소 하나의 @Entity 클래스 필요 -> @WebMvcTest 에는 @Entity 없음
 * 문제점: @EnableJpaAuditing 이 @SpringBootApplication 과 같이 있다보니 @WebMvcTest 가 저걸 스캔하게 됨
 * 해결: @EnableJpaAuditing, @SpringBootApplication 분리
 */
@WebMvcTest(controllers = HelloController.class, // MockMvc 자동 주입 + controller 범위 제한
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            }) //[excludeFilters 속성 -> 스캔 대상에서 제외할 클래스 제거)
class HelloControllerTest {

    @Autowired MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    void returnHello() throws Exception {
        //given
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    void returnHelloDto() throws Exception {
        String name = "test";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(name)))
                .andExpect(jsonPath("$.amount", Matchers.is(amount)));
    }
}