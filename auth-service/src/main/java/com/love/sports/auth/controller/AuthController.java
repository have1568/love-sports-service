package com.love.sports.auth.controller;

import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.service.SysUserInfoService;
import com.love.sports.auth.web.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/auth")
public class AuthController implements BaseController<Object> {

    @Resource
    private SysUserInfoService sysUserInfoService;

    /**
     * 注册用户
     */
    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@RequestBody @Validated SysUserInfo sysUserInfo) {
        return ok(sysUserInfoService.save(sysUserInfo));
    }

    @GetMapping(value = "/userinfo")
    public ResponseEntity<Object> info(Authentication authentication) {
        return ok(authentication.getPrincipal());
    }

}
