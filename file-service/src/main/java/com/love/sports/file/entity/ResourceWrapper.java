package com.love.sports.file.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

/**
 * @author WangXinzhu
 * @date 2022/2/16 16:17
 * @since 1.0
 */
@Data
@Builder
public class ResourceWrapper {
    /**
     *  文件类型对应的 mediaType
     */
    private MediaType mediaType;

    /**
     *  输出流
     */
    private Resource resource;

    public ResourceWrapper(MediaType mediaType, Resource resource) {
        this.mediaType = mediaType;
        this.resource = resource;
    }
}
