package com.love.sports.user.controller;

import com.love.sports.user.web.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@RestController
@RequestMapping(path = "/auth")
@SessionAttributes({"authorizationRequest"})
public class AuthController implements BaseController<Object> {

    @GetMapping(value = "/userinfo")
    public Principal info(Principal principal) {
        return principal;
    }

}
