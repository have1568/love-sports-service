package com.love.sports.file.controller;

import com.love.sports.file.entity.model.FileDetails;
import com.love.sports.file.service.FileDetailsService;
import com.love.sports.file.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 控制层
 *
 * @author have1568
 * @since 2022-02-13 16:39:01
 */
@Slf4j
@RestController
@RequestMapping("/api/fileDetails")
public class FileDetailsController implements BaseController<FileDetails> {

    @Resource
    private FileDetailsService fileDetailsService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ResponseEntity<FileDetails> save(@RequestBody @Validated FileDetails fileDetails) {
        return ok(fileDetailsService.save(fileDetails));
    }

    /**
     * 获取
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<FileDetails> findById(@PathVariable("id") String id) {
        return ok(fileDetailsService.findById(id));
    }

    /**
     * 获取列表(分页)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<FileDetails>> pageQuery(FileDetails fileDetails, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FileDetails> page = fileDetailsService.findByCondition(fileDetails, pageable);
        return ok(page);
    }

    /**
     * 修改
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Validated FileDetails fileDetails, @PathVariable("id") String id) {
        return ok(fileDetailsService.update(fileDetails, id));
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ok(fileDetailsService.deleteById(id));
    }

}

