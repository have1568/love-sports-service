package com.love.sports.user.controller;


import java.lang.String;
import java.util.List;
import java.util.Map;

import com.love.sports.user.common.Res;
import com.love.sports.user.entity.model.SysClientDetail;
import com.love.sports.user.service.SysClientDetailService;
import com.love.sports.user.web.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 控制层
 *
 * @author have1568
 * @since 2021-12-06 19:55:19
 */
@Slf4j
@Api("控制层")
@RestController
@RequestMapping("/api/client")
public class SysClientDetailController implements BaseController<SysClientDetail> {

    @Resource
    private SysClientDetailService sysClientDetailService;

    /**
     * 获取列表(分页)
     */
    @GetMapping("/list")
    public Res<Page<SysClientDetail>> pageQuery(SysClientDetail sysClientDetail, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SysClientDetail> page = sysClientDetailService.findByCondition(sysClientDetail, pageable);
        return ok(page);
    }

    /**
     * 获取所有用于选择附件
     * @return
     */
    @GetMapping("/all")
    public Res<List<Map<String, Object>>> findAllForSelect() {
        return Res.success(sysClientDetailService.findAllForSelect());
    }

    /**
     * 获取
     */
    @GetMapping("/get/{id}")
    public Res<SysClientDetail> findById(@PathVariable("id") String id) {
        return ok(sysClientDetailService.findById(id));
    }

    /**
     * 添加
     */
    @PostMapping("/save")
    public Res<SysClientDetail> save(@RequestBody @Validated SysClientDetail sysClientDetail) {
        return ok(sysClientDetailService.save(sysClientDetail));
    }


    /**
     * 修改
     */
    @PostMapping("/update/{id}")
    public Res<Boolean> update(@RequestBody @Validated SysClientDetail sysClientDetail, @PathVariable("id") String id) {
        return ok(sysClientDetailService.update(sysClientDetail, id));
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    public Res<Boolean> delete(@PathVariable("id") String id) {
        return ok(sysClientDetailService.deleteById(id));
    }

}

