package com.love.sports.user.repository;

import com.love.sports.user.LoveSportsAuthApplicationTests;
import com.love.sports.user.entity.model.SysUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class SysUserInfoRepositoryTest extends LoveSportsAuthApplicationTests {

    @Resource
    SysUserInfoRepository sysUserInfoRepository;

    @Test
    void count() {
        long count = sysUserInfoRepository.count(Example.of(new SysUserInfo()));
        System.out.println(count);
    }
}