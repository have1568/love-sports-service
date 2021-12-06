package com.love.sports.user.controller;

import com.love.sports.user.entity.model.SysDept;
import com.love.sports.user.service.SysDeptService;
import com.love.sports.user.web.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api("部门管理")
@RestController
@RequestMapping("/api/dept")
public class SysDeptController implements BaseController<SysDept> {

    @Resource
    private SysDeptService sysDeptService;

    @PostMapping("/save")
    public ResponseEntity<SysDept> save(@RequestBody @Validated SysDept sysDept) {
        return ok(sysDeptService.save(sysDept));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SysDept> findById(@PathVariable("id") int id) {
        SysDept sysDept = sysDeptService.findById(id);
        return ok(sysDept);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        sysDeptService.deleteById(id);
        return ok(sysDeptService.deleteById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<SysDept>> pageQuery(SysDept sysDept, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SysDept> sysDeptPage = sysDeptService.findByCondition(sysDept, pageable);
        return ok(sysDeptPage);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Validated SysDept sysDept, @PathVariable("id") int id) {
        return ok(sysDeptService.update(sysDept, id));
    }
}