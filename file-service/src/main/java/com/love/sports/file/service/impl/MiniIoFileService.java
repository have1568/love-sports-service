package com.love.sports.file.service.impl;

import com.love.sports.file.config.MinioProperties;
import com.love.sports.file.entity.ResourceWrapper;
import com.love.sports.file.entity.model.FileDetails;
import com.love.sports.file.exception.FileException;
import com.love.sports.file.service.FileDetailsService;
import com.love.sports.file.service.FileService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author WangXinzhu
 * @date 2022/2/13 15:39
 * @since 1.0
 */
@Slf4j
@Service
@ConditionalOnClass({MinioClient.class})
public class MiniIoFileService implements FileService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    private final FileDetailsService fileDetailsService;

    public MiniIoFileService(MinioClient minioClient, MinioProperties minioProperties, FileDetailsService fileDetailsService) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
        this.fileDetailsService = fileDetailsService;
    }


    @Override
    @Transactional
    public String saveFile(MultipartFile file) {

        FileDetails fileDetails = fileDetailsService.createAndSave(file, "");

        // Create a InputStream for object upload.
        try (ByteArrayInputStream bytes = new ByteArrayInputStream(file.getBytes())) {

            minioClient.putObject(
                    PutObjectArgs.builder().
                            bucket(minioProperties.getBucket()).object(fileDetails.getId() + "." + fileDetails.getExtension())
                            .stream(bytes, bytes.available(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException | IOException e) {
            throw new FileException("file upload error");
        }

        return fileDetails.getFileDownloadUri();
    }

    @Override
    public ResourceWrapper loadFileAsResource(String fileId) {

        FileDetails fileDetails = fileDetailsService.findById(fileId);
        if (fileDetails == null) {
            throw new FileException("File not found ");
        }
        try {
            GetObjectResponse inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(fileDetails.getFileName()).build());
            Resource resource = new InputStreamResource(inputStream, fileDetails.getContentType());
            if (resource.exists()) {
                return ResourceWrapper.builder().resource(resource).mediaType(MediaType.parseMediaType(fileDetails.getContentType())).build();
            } else {
                throw new FileException("File not found " + fileDetails.getFileName());
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw new FileException("File not found ", e);
        }

    }
}
