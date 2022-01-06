package com.love.sports.auth.repository;

import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.auth.entity.model.SysUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;

import javax.annotation.Resource;

class SysUserInfoRepositoryTest extends LoveSportsAuthApplicationTests {

    @Resource
    SysUserInfoRepository sysUserInfoRepository;

    @Test
    void count() {
        long count = sysUserInfoRepository.count(Example.of(new SysUserInfo()));
        System.out.println(count);
    }
}