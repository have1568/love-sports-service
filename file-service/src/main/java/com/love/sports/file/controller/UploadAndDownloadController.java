package com.love.sports.file.controller;

import com.love.sports.file.entity.ResourceWrapper;
import com.love.sports.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangXinzhu
 * @date 2022/2/13 14:37
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
public class UploadAndDownloadController {


    private final FileService fileService;

    public UploadAndDownloadController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.saveFile(file));
    }


    @PostMapping("/batchUpload")
    public ResponseEntity<List<String>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(fileService.batchSaveFile(files));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        // Load file as Resource
        ResourceWrapper resourceWrapper = fileService.loadFileAsResource(id);

        // Fallback to the default content type if type could not be determined
        if (resourceWrapper.getResource() == null) {
            resourceWrapper.setMediaType(MediaType.APPLICATION_OCTET_STREAM);
        }

        return ResponseEntity.ok()
                .contentType(resourceWrapper.getMediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceWrapper.getResource().getFilename() + "\"")
                .body(resourceWrapper.getResource());
    }
}