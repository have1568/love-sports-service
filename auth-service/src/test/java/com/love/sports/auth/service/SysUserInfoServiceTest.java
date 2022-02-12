package com.love.sports.auth.service;

import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.auth.entity.model.SysUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

class SysUserInfoServiceTest extends LoveSportsAuthApplicationTests {

    @Resource
    SysUserInfoService sysUserInfoService;

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
        sysUserInfoService.findById("1");
    }

    @Test
    void findByCondition() {
        sysUserInfoService.findByCondition(new SysUserInfo(), PageRequest.of(0, 10));
    }

    @Test
    void findByJpaQuery() {
    }
}