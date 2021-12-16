package com.love.sports.user.web;

import com.love.sports.user.common.Res;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public Object test(){
        return Res.success("test");
    }
}
