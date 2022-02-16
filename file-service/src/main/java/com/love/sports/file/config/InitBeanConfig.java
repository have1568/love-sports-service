package com.love.sports.file.config;

import com.love.sports.file.service.FileService;
import com.love.sports.file.service.impl.MiniIoFileService;
import com.love.sports.file.service.impl.MongoFileService;
import com.love.sports.file.service.impl.SimpleFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

@Configuration
public class InitBeanConfig {


    public static final String MONGO_FILE = "gridFs";
    public static final String MINIO = "minio";

    @Value(value = "${file.store}")
    private String storeType;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private MiniIoFileService miniIoFileService;

    @Resource
    private MongoFileService mongoFileService;

    @Resource
    private SimpleFileService simpleFileService;


    public InitBeanConfig() {
    }


    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public DefaultTokenServices defaultTokenServices() {
        // 配置tokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

    @Bean
    public FileService fileService() {
        switch (storeType) {
            case MONGO_FILE:
                return mongoFileService;
            case MINIO:
                return miniIoFileService;
            default:
                return simpleFileService;
        }
    }
}
