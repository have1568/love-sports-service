package com.love.sports.user.controller;

import com.love.sports.user.common.Res;
import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.service.SysUserInfoService;
import com.love.sports.user.web.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
@RequestMapping(path = "/auth")
@SessionAttributes({"authorizationRequest"})
public class AuthController implements BaseController<Object> {


    @Resource
    private SysUserInfoService sysUserInfoService;

    /**
     * 注册用户
     */
    @PostMapping(path = "/register")
    public Res<Void> register(@RequestBody @Validated SysUserInfo sysUserInfo) {
        sysUserInfoService.save(sysUserInfo);
        return ok();
    }

    @GetMapping(value = "/userinfo")
    public Principal info(Principal principal) {
        return principal;
    }

}