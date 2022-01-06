package com.love.sports.auth.controller;


import com.love.sports.auth.entity.model.SysClientDetail;
import com.love.sports.auth.service.SysClientDetailService;
import com.love.sports.auth.web.BaseController;
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
    public ResponseEntity<Page<SysClientDetail>> pageQuery(SysClientDetail sysClientDetail, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ok(sysClientDetailService.findByCondition(sysClientDetail, pageable));
    }

    /**
     * 获取所有
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<SysClientDetail>> findAllForSelect() {
        return ok(sysClientDetailService.findAllForSelect());
    }

    /**
     * 获取
     *
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<SysClientDetail> findById(@PathVariable("id") String id) {
        return ok(sysClientDetailService.findById(id));
    }

    /**
     * 添加
     *
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<SysClientDetail> save(@RequestBody @Validated SysClientDetail sysClientDetail) {
        return ok(sysClientDetailService.save(sysClientDetail));
    }


    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Validated SysClientDetail sysClientDetail, @PathVariable("id") String id) {
        return ok(sysClientDetailService.update(sysClientDetail, id));
    }

    /**
     * 删除
     *
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ok(sysClientDetailService.deleteById(id));
    }

}

