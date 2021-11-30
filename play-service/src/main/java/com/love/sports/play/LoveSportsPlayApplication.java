package com.love.sports.play;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LoveSportsPlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsPlayApplication.class, args);
    }

}
