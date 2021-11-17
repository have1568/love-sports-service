package com.love.sports.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableAuthorizationServer
@EnableDiscoveryClient
@SpringBootApplication
public class LoveSportsUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsUserApplication.class, args);
    }

}
