package com.love.sports.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class LoveSportsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsAuthApplication.class, args);
    }

}
