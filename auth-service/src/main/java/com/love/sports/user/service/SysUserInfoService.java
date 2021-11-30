package com.love.sports.user.service;


import com.love.sports.user.entity.domain.SysUserInfo;
import com.love.sports.user.repo.SysUserInfoRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class SysUserInfoService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    public UserDetails authUserByUsername(String username) {
        SysUserInfo sysUserInfoExample = new SysUserInfo();
        sysUserInfoExample.setUsername(username);
        List<SysUserInfo> userInfos = sysUserInfoRepository.findAll(Example.of(sysUserInfoExample));
        SysUserInfo userInfo;
        if (userInfos.isEmpty()) {
            throw new UsernameNotFoundException("用户名不存在:" + username);
        } else {
            userInfo = userInfos.get(0);
        }
        return new User(userInfo.getUsername(), userInfo.getPassword(), true, true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
