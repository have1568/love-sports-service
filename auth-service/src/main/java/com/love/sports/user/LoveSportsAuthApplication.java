package com.love.sports.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
//@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS}, maxAge = 1800)
public class LoveSportsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsAuthApplication.class, args);
    }

}
