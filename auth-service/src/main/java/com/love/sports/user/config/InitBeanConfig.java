package com.love.sports.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Configuration
public class InitBeanConfig {

    @Resource
    private RedisIndexedSessionRepository sessionRepository;

    @Bean
    public SessionRegistry sessionRegistry() {
        sessionRepository.setDefaultMaxInactiveInterval(2100);
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
