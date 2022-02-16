package com.love.sports.file.service.impl;

import com.love.sports.file.entity.ResourceWrapper;
import com.love.sports.file.entity.model.FileDetails;
import com.love.sports.file.exception.FileException;
import com.love.sports.file.service.FileDetailsService;
import com.love.sports.file.service.FileService;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author WangXinzhu
 * @date 2022/2/13 15:39
 * @since 1.0
 */
@Service
public class MongoFileService implements FileService {

    @Resource
    private GridFsTemplate gridFsTemplate;

    @Resource
    private MongoTemplate mongoTemplate;


    @Resource
    private FileDetailsService fileDetailsService;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
            FileDetails fileDetails = fileDetailsService.createAndSave(file, objectId.toString());
            return fileDetails.getFileDownloadUri();
        } catch (IOException e) {
            throw new FileException("save file error", e);
        }


    }


    @Override
    public ResourceWrapper loadFileAsResource(String fileId) {
        FileDetails fileDetails = fileDetailsService.findById(fileId);
        if (fileDetails == null) {
            throw new FileException("File not found ");
        }
        Query query = Query.query(Criteria.where("_id").is(fileDetails.getTargetLocation()));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        GridFSDownloadStream gridFSDownloadStream = GridFSBuckets.create(mongoTemplate.getDb()).openDownloadStream(gridFSFile.getObjectId());
        org.springframework.core.io.Resource resource = new InputStreamResource(gridFSDownloadStream, fileDetails.getContentType());
        if (resource.exists()) {
            return ResourceWrapper.builder().resource(resource).mediaType(MediaType.parseMediaType(fileDetails.getContentType())).build();
        } else {
            throw new FileException("File not found " + fileDetails.getFileName());
        }
    }
}
