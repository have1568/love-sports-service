package com.love.sports.file.service.impl;

import com.love.sports.file.config.FileProperties;
import com.love.sports.file.entity.ResourceWrapper;
import com.love.sports.file.entity.model.FileDetails;
import com.love.sports.file.exception.FileException;
import com.love.sports.file.service.FileDetailsService;
import com.love.sports.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author WangXinzhu
 * @date 2022/2/13 14:39
 * @since 1.0
 */
@Service
public class SimpleFileService implements FileService {

    private final Path fileStorageLocation; // 文件在本地存储的地址

    private final FileDetailsService fileDetailsService;



    @Autowired
    public SimpleFileService(FileProperties fileProperties, FileDetailsService fileDetailsService) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        this.fileDetailsService = fileDetailsService;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    /**
     * 加载文件
     *
     * @param fileId 文件ID
     * @return 文件
     */
    @Override
    public ResourceWrapper loadFileAsResource(String fileId) {
        try {
            FileDetails fileDetails = fileDetailsService.findById(fileId);
            if (fileDetails == null) {
                throw new FileException("File not found ");
            }
            Path filePath = Paths.get(fileDetails.getTargetLocation());

            InputStream inputStream = Files.newInputStream(filePath);


            Resource resource = new InputStreamResource(inputStream,fileDetails.getContentType());
            if (resource.exists()) {
                return ResourceWrapper.builder().resource(resource).mediaType(MediaType.parseMediaType(fileDetails.getContentType())).build();
            } else {
                throw new FileException("File not found " + fileDetails.getFileName());
            }
        } catch (IOException ex) {
            throw new FileException("File not found ", ex);
        }
    }

    /**
     * 存储文件到系统
     *
     * @param file 文件
     * @return 文件名
     */
    @Override
    public String saveFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {

            // Copy file to the target location (Replacing existing file with the same name)
            //检查是否有同名文件
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // 复制文件到本地磁盘
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            //保存文件信息到数据库
            FileDetails details = fileDetailsService.createAndSave(file, targetLocation.toString());

            return details.getFileDownloadUri();
        } catch (IOException ex) {
            throw new FileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
