package com.love.sports.user.controller;


import com.love.sports.user.entity.model.SysResources;
import com.love.sports.user.service.SysResourcesService;
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
import java.util.Collection;


/**
 * 控制层
 *
 * @author makejava
 * @since 2021-12-05 17:56:05
 */
@Slf4j
@Api("控制层")
@RestController
@RequestMapping("/api/resources")
public class SysResourcesController implements BaseController<SysResources> {

    @Resource
    private SysResourcesService sysResourcesService;

    /**
     * 获取列表(分页)
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Page<SysResources>> pageQuery(SysResources sysResources, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SysResources> page = sysResourcesService.findByCondition(sysResources, pageable);
        return ok(page);
    }

    /**
     * 获取所有用于选择夫夫资源选择
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<SysResources>> findAllForSelect() {
        return ok(sysResourcesService.findAllForSelect());
    }


    /**
     * 获取所有用于选择附件
     *
     * @return
     */
    @GetMapping("/all/tree")
    public ResponseEntity<Collection<SysResources>> currentUserAllowTreeResources(@RequestParam String userId) {
        return ResponseEntity.ok(sysResourcesService.currentUserAllowTreeResources(userId));
    }

    /**
     * 获取
     *
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<SysResources> findById(@PathVariable("id") Integer id) {
        return ok(sysResourcesService.findById(id));
    }

    /**
     * 添加
     *
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<SysResources> save(@RequestBody @Validated SysResources sysResources) {
        return ok(sysResourcesService.save(sysResources));
    }


    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Validated SysResources sysResources, @PathVariable("id") Integer id) {
        return ok(sysResourcesService.update(sysResources, id));
    }

    /**
     * 删除
     *
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ok(sysResourcesService.deleteById(id));
    }

}

