package com.love.sports.auth;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
@EnableDubbo
public class LoveSportsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsAuthApplication.class, args);
    }

}
