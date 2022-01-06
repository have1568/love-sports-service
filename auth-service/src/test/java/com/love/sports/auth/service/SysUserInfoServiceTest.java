package com.love.sports.auth.service;

import com.love.sports.auth.LoveSportsAuthApplicationTests;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void findByJpaQuery() {
    }
}