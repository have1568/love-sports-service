package com.love.sports.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

/**
 * @author WangXinzhu
 * @date 2022/2/16 13:34
 * @since 1.0
 */
@Data
@Profile("minio")
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     连接地址
     */
    private String endpoint;

    /**
     * 端口
     */
    private int port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 存储桶
     */
    private String bucket;
}
