package com.love.sports.user.controller;


import com.love.sports.user.entity.model.SysRole;
import com.love.sports.user.service.SysRoleService;
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


/**
 * 角色表控制层
 *
 * @author makejava
 * @since 2021-12-05 17:48:53
 */
@Slf4j
@Api("角色表控制层")
@RestController
@RequestMapping("/api/role")
public class SysRoleController implements BaseController<SysRole> {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 获取角色表列表(分页)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<SysRole>> pageQuery(SysRole sysRole, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SysRole> page = sysRoleService.findByCondition(sysRole, pageable);
        return ok(page);
    }

    /**
     * 获取角色表
     */
    @GetMapping("/{id}")
    public ResponseEntity<SysRole> findById(@PathVariable("id") Integer id) {
        return ok(sysRoleService.findById(id));
    }

    /**
     * 添加角色表
     */
    @PostMapping("/save")
    public ResponseEntity<SysRole> save(@RequestBody @Validated SysRole sysRole) {
        return ok(sysRoleService.save(sysRole));
    }


    /**
     * 修改角色表
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Validated SysRole sysRole, @PathVariable("id") Integer id) {
        return ok(sysRoleService.update(sysRole, id));
    }

    /**
     * 删除角色表
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ok(sysRoleService.deleteById(id));
    }

}

