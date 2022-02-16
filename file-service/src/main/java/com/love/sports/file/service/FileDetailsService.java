package com.love.sports.file.service;


import com.love.sports.file.entity.model.FileDetails;
import com.love.sports.file.exception.FileException;
import com.love.sports.file.repository.FileDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


/**
 * 业务层
 *
 * @author have1568
 * @since 2022-02-13 16:39:03
 */
@Slf4j
@Service
public class FileDetailsService {

    @Resource
    private FileDetailsRepository fileDetailsRepository;

    @Value(value = "${file.base-url}")
    private String baseUrl;

    @Transactional
    public FileDetails save(FileDetails fileDetails) {
        return fileDetailsRepository.save(fileDetails);
    }


    @Transactional
    public boolean deleteById(String id) {
        Optional<FileDetails> optional = fileDetailsRepository.findById(id);
        if (optional.isPresent()) {
            delete(id);
            return true;
        }
        return false;
    }

    public void delete(String id) {
        fileDetailsRepository.deleteById(id);
    }

    @Transactional
    public boolean update(FileDetails fileDetails, String id) {
        Assert.notNull(fileDetails, "数据不存在");
        boolean exist = fileDetailsRepository.existsById(id);
        if (!exist) {
            return false;
        }
        fileDetailsRepository.save(fileDetails);
        return true;
    }

    public FileDetails findById(String id) {
        Assert.notNull(id, "查询Id不能为空");
        return fileDetailsRepository.findById(id).orElse(null);
    }


    public Page<FileDetails> findByCondition(FileDetails fileDetails, Pageable page) {
        if (fileDetails == null) {
            return fileDetailsRepository.findAll(page);
        }
        Example<FileDetails> example = Example.of(fileDetails);
        return fileDetailsRepository.findAll(example, page);
    }


    @Transactional
    public FileDetails createAndSave(MultipartFile file, String targetLocation) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        String uuid = UUID.randomUUID().toString();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String extension = file.getOriginalFilename().split("\\.")[1];
        String name = uuid + "." + extension;
        FileDetails details = FileDetails.builder()
                .id(uuid)
                .fileName(name)
                .fileOriginalName(file.getOriginalFilename())
                .extension(extension)
                .contentType(file.getContentType())
                .size(file.getSize())
                .createAt(LocalDateTime.now())
                .createBy(userDetails.getUsername())
                .updateAt(LocalDateTime.now())
                .updateBy(userDetails.getUsername())
                .targetLocation(targetLocation)
                .fileDownloadUri(baseUrl + uuid)
                .delFlag(Boolean.FALSE)
                .build();

        save(details);
        return details;
    }
}

