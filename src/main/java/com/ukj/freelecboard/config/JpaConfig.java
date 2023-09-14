package com.ukj.freelecboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication 와 분리
@Configuration
@EnableJpaAuditing //JPA Auditing 활성화
public class JpaConfig {
}
