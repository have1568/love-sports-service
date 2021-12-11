package com.love.sports.user.controller;


import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.service.SysUserInfoService;
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
 * 控制层
 *
 * @author wang
 * @since 2021-12-05 17:58:59
 */
@Slf4j
@Api("控制层")
@RestController
@RequestMapping("/api/user")
public class SysUserInfoController implements BaseController<SysUserInfo> {

    @Resource
    private SysUserInfoService sysUserInfoService;

    /**
     * 获取列表(分页)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<SysUserInfo>> pageQuery(SysUserInfo sysUserInfo, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SysUserInfo> page = sysUserInfoService.findByCondition(sysUserInfo, pageable);
        return ok(page);
    }

    /**
     * 获取
     */
    @GetMapping("/{id}")
    public ResponseEntity<SysUserInfo> findById(@PathVariable("id") String id) {
        return ok(sysUserInfoService.findById(id));
    }

    /**
     * 添加
     */
    @PostMapping("/save")
    public ResponseEntity<SysUserInfo> save(@RequestBody @Validated SysUserInfo sysUserInfo) {
        return ok(sysUserInfoService.save(sysUserInfo));
    }


    /**
     * 修改
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Validated SysUserInfo sysUserInfo, @PathVariable("id") String id) {
        return ok(sysUserInfoService.update(sysUserInfo, id));
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ok(sysUserInfoService.deleteById(id));
    }


    /**
     * 注册用户
     */
    @PostMapping(path = "/register")
    public ResponseEntity<Void> register(@RequestBody @Validated SysUserInfo sysUserInfo) {
        sysUserInfoService.save(sysUserInfo);
        return ok();
    }

}

