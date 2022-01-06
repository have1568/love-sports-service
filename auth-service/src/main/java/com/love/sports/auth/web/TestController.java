package com.love.sports.auth.web;

import com.love.sports.auth.common.Res;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public Object test(){
        return Res.success("test");
    }
}
