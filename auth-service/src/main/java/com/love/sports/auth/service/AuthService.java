package com.love.sports.auth.service;


import com.love.sports.auth.config.impl.UserDetailsServiceImpl;
import com.love.sports.auth.entity.model.AuditModel;
import com.love.sports.auth.entity.model.SysRole;
import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.event.*;
import com.love.sports.auth.repository.SysUserInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;


@Service
public class AuthService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private PhoneCheckEventStore phoneCheckEventStore;

    @Resource
    private EmailCheckEventStore emailCheckEventStore;

    @Resource
    private ResetPasswordEventStore resetPasswordEventStore;

    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Transactional
    public SysUserInfo register(SysUserInfo sysUserInfo) {

        verifyRegisterInputInfo(sysUserInfo);

        Set<SysRole> set = new HashSet<>();
        SysRole defaultRole = sysRoleService.getDefaultRole();
        set.add(defaultRole);
        sysUserInfo.setRoles(set);
        sysUserInfo.setPassword(passwordEncoder.encode(sysUserInfo.getPassword()));
        sysUserInfo.setStatus(AuditModel.Status.INIT);

        SysUserInfo savedUser = sysUserInfoRepository.save(sysUserInfo);

        //TODO 发送激活邮件或者激活验证码

        return savedUser;

    }

    private void verifyRegisterInputInfo(SysUserInfo sysUserInfo) {
        List<SysUserInfo> usernames = sysUserInfoRepository.findByUsernameAndDelFlag(sysUserInfo.getUsername(), Boolean.FALSE);
        List<SysUserInfo> emails = sysUserInfoRepository.findByEmailAndDelFlag(sysUserInfo.getEmail(), Boolean.FALSE);
        List<SysUserInfo> phoneNumbers = sysUserInfoRepository.findByPhoneNumberAndDelFlag(sysUserInfo.getPhoneNumber(), Boolean.FALSE);

        if (!usernames.isEmpty()) {
            throw new RuntimeException("账号已存在！");
        }

        if (!emails.isEmpty()) {
            throw new RuntimeException("邮箱已存在！");
        }

        if (!phoneNumbers.isEmpty()) {
            throw new RuntimeException("手机号已存在！");
        }
    }


    public ResetPasswordEvent resetPassword(ResetPasswordEvent inputEvent) {
        ResetPasswordEvent storeEvent = resetPasswordEventStore.readEvent(inputEvent.getEventId());
        Assert.notNull(storeEvent, "非法请求");
        SysUserInfo sysUserInfo = sysUserInfoRepository.findById(storeEvent.getUserId()).orElse(null);
        Assert.notNull(sysUserInfo, "用户不存在");

        sysUserInfo.setPassword(passwordEncoder.encode(inputEvent.getPassword()));
        sysUserInfoRepository.save(sysUserInfo);

        resetPasswordEventStore.removeEvent(storeEvent.getEventId());
        return storeEvent;


    }

    public Event checkEmail(String email) {
        List<SysUserInfo> emails = sysUserInfoRepository.findByEmailAndDelFlag(email, Boolean.FALSE);

        if (emails.isEmpty()) {
            throw new IllegalArgumentException("邮箱不存在！");
        }

        EmailCheckEvent emailCheckEvent = EmailCheckEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .email(email)
                .code(getRandom())
                .userId(emails.get(0).getId())
                .build();

        emailCheckEventStore.store(emailCheckEvent);
        return emailCheckEvent;
    }

    public PhoneCheckEvent checkPhoneNumber(String phoneNumber) {
        List<SysUserInfo> phoneNumbers = sysUserInfoRepository.findByPhoneNumberAndDelFlag(phoneNumber, Boolean.FALSE);
        if (phoneNumbers.isEmpty()) {
            throw new IllegalArgumentException("手机号不存在！");
        }

        PhoneCheckEvent resetPasswordEvent = PhoneCheckEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .code(getRandom())
                .userId(phoneNumbers.get(0).getId())
                .build();
        phoneCheckEventStore.store(resetPasswordEvent);
        return resetPasswordEvent;
    }

    public ResetPasswordEvent checkPhoneCode(PhoneCheckEvent inputEvent) {
        PhoneCheckEvent storeEvent = phoneCheckEventStore.readEvent(inputEvent.getEventId());
        Assert.notNull(storeEvent, "验证码不存在或已过期");
        if (!storeEvent.getCode().equals(inputEvent.getCode())) {
            throw new IllegalArgumentException("验证码不正确！");
        }

        phoneCheckEventStore.removeEvent(storeEvent.getEventId());
        ResetPasswordEvent resetPasswordEvent = ResetPasswordEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .userId(storeEvent.getUserId())
                .build();

        resetPasswordEventStore.store(resetPasswordEvent);
        return resetPasswordEvent;
    }


    public ResetPasswordEvent checkEmailCode(EmailCheckEvent inputEvent) {
        EmailCheckEvent storeEvent = emailCheckEventStore.readEvent(inputEvent.getEventId());
        Assert.notNull(storeEvent, "验证码不存在或已过期");
        if (!storeEvent.getCode().equals(inputEvent.getCode())) {
            throw new IllegalArgumentException("验证码不正确！");
        }
        phoneCheckEventStore.removeEvent(storeEvent.getEventId());
        ResetPasswordEvent resetPasswordEvent = ResetPasswordEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .userId(storeEvent.getUserId())
                .build();

        resetPasswordEventStore.store(resetPasswordEvent);
        return resetPasswordEvent;
    }


    private String getRandom() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(10); //每次随机出一个数字（0-9）
            code.append(r);  //把每次随机出的数字拼在一起
        }
        return code.toString();
    }

    public Object getUserInfo(Authentication authentication) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetailsServiceImpl.loadUserByUsername(userDetails.getUsername());
    }
}
