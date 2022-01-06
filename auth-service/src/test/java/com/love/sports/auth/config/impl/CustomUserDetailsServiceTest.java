package com.love.sports.auth.config.impl;

import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.outs.LoginOutput;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class CustomUserDetailsServiceTest extends LoveSportsAuthApplicationTests {

    @Resource
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    void loadUserByUsername() {
        LoginOutput userDetails = (LoginOutput) userDetailsServiceImpl.loadUserByUsername("have1568");
        System.out.println(userDetails);
    }
}