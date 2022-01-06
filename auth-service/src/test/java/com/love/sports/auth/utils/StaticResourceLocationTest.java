package com.love.sports.auth.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;

public class StaticResourceLocationTest {

    @Test
    void test(){
        for (StaticResourceLocation value : StaticResourceLocation.values()) {
            System.out.println(value.getPatterns().findFirst().get());
        }
    }
}
