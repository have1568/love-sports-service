package com.love.sports.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class LoveSportsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsAuthApplication.class, args);
    }

}
