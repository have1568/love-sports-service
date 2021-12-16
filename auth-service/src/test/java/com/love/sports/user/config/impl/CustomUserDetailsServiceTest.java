package com.love.sports.user.config.impl;

import com.love.sports.user.LoveSportsAuthApplicationTests;
import com.love.sports.user.entity.out.LoginOutput;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class CustomUserDetailsServiceTest extends LoveSportsAuthApplicationTests {

    @Resource
    CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername() {
        LoginOutput userDetails = (LoginOutput) customUserDetailsService.loadUserByUsername("have1568");
    }
}