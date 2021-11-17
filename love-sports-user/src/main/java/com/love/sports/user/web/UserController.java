package com.love.sports.user.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController {


    @PostMapping(path = "/login")
    public Object login() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("data", 1);
        res.put("msg", "SUCCESS");

        return res;
    }
}
