package com.love.sports.auth.controller;

import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.event.EmailCheckEvent;
import com.love.sports.auth.event.PhoneCheckEvent;
import com.love.sports.auth.event.ResetPasswordEvent;
import com.love.sports.auth.service.AuthService;
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
    private AuthService authService;

    /**
     * 注册用户
     */
    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@RequestBody @Validated SysUserInfo sysUserInfo) {
        return ok(authService.register(sysUserInfo));
    }

    /**
     * 获取用户信息
     */
    @GetMapping(value = "/userinfo")
    public ResponseEntity<Object> info(Authentication authentication) {
        return ok(authentication.getPrincipal());
    }

    /**
     * 校验邮件
     */
    @PostMapping(value = "/check/email")
    public ResponseEntity<Object> checkEmail(@RequestParam final String email) {
        return ok(authService.checkEmail(email));
    }

    /**
     * 校验手机号
     */
    @PostMapping(value = "/check/phone")
    public ResponseEntity<Object> checkPhone(@RequestParam final String phoneNumber) {
        return ok(authService.checkPhoneNumber(phoneNumber));
    }

    /**
     * 校验手机发送验证码
     */
    @PostMapping(value = "/check/phone/code")
    public ResponseEntity<Object> checkPhoneCode(@RequestBody @Validated PhoneCheckEvent phoneCheckEvent) {
        return ok(authService.checkPhoneCode(phoneCheckEvent));
    }

    /**
     * 校验邮箱发送验证码
     */
    @PostMapping(value = "/check/email/code")
    public ResponseEntity<Object> checkEmailCode(@RequestBody @Validated EmailCheckEvent inputEvent) {
        return ok(authService.checkEmailCode(inputEvent));
    }

    /**
     * 重置密码
     */
    @PostMapping(value = "/reset/password")
    public ResponseEntity<Object> resetPassword(@RequestBody @Validated ResetPasswordEvent resetPasswordEvent) {
        return ok(authService.resetPassword(resetPasswordEvent));
    }

}
