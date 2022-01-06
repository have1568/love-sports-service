package com.love.sports.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
//@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS}, maxAge = 1800)
public class LoveSportsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsAuthApplication.class, args);
    }

}
