package com.love.sports.file.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangXinzhu
 * @date 2022/2/16 13:55
 * @since 1.0
 */
@Configuration
public class MinioConfig {

    private final MinioProperties minioProperties;

    public MinioConfig(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint(), minioProperties.getPort(), false)
                .credentials(minioProperties.getUsername(), minioProperties.getPassword())
                .build();
    }


}
