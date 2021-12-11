package com.love.sports.user.utils;

import com.love.sports.user.LoveSportsAuthApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

public class PasswordEncoderTest extends LoveSportsAuthApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;

    public static final String PASSWORD = "$2a$10$rlQjH/D7FMkndMQN29.Uyux7hls6twxihjwaSy.pw4A5aIYlhgNx6";

    @Test
    public void  encodeTest(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);

    }

    @Test
    public void  matchesTest(){
        System.out.println(passwordEncoder.matches("123456",PASSWORD));
    }



}
