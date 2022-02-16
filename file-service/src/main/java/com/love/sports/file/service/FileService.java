package com.love.sports.file.service;

import com.love.sports.file.entity.ResourceWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangXinzhu
 * @date 2022/2/13 15:24
 * @since 1.0
 */
public interface FileService {

    /**
     *  保存文件
     * @param file MultipartFile 文件类型
     * @return 返回文件的下载地址
     */
    String saveFile(MultipartFile file);

    /**
     * 批量保存文件
     * @param files MultipartFile 文件类型
     * @return 返回文件地址
     */
    default List<String> batchSaveFile(MultipartFile[] files) {
        return Arrays.stream(files).map(this::saveFile).collect(Collectors.toList());
    }


    /**
     * 将文件加载为 Resource
     * @param fileId 文件ID(存在数据库的文件ID)
     * @return 返回 Resource 和 MediaType
     */
    ResourceWrapper loadFileAsResource(String fileId);
}
