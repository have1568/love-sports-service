package com.love.sports.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author WangXinzhu
 * @date 2022/2/13 14:40
 * @since 1.0
 */
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}