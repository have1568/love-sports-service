package com.love.sports.file;

import com.love.sports.file.config.FileProperties;
import com.love.sports.file.config.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({FileProperties.class, MinioProperties.class})
public class LoveSportsFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveSportsFileApplication.class, args);
    }

}
