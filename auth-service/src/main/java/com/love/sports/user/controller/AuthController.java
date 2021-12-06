package com.love.sports.user.controller;

import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.service.SysUserInfoService;
import com.love.sports.user.web.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthController implements BaseController<Object> {

    @Resource
    private SysUserInfoService sysUserInfoService;

    @GetMapping(value = "/userinfo")
    public Object user(@NotNull OAuth2Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", authentication.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", authentication.getUserAuthentication().getAuthorities());
        return userInfo;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<Void> register(@RequestBody @Validated SysUserInfo sysUserInfo) {
        sysUserInfoService.save(sysUserInfo);
        return ok();
    }
}
