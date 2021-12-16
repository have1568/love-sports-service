package com.love.sports.user.config.impl;

import com.love.sports.user.entity.model.CommonModel;
import com.love.sports.user.entity.model.SysResources;
import com.love.sports.user.entity.model.SysRole;
import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.entity.out.LoginOutput;
import com.love.sports.user.repository.SysUserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    public static final String DEFAULT_ROLE = "ROLE_USER";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SysUserInfo> userInfos = sysUserInfoRepository.findByUsernameAndIsDeleted(username, false);
        SysUserInfo sysUser;

        if (userInfos.isEmpty()) {
            throw new UsernameNotFoundException("用户不存在:" + username);
        } else {
            sysUser = userInfos.get(0);
        }

        //构建只包含角色KEY和资源PATH 的权限集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        //用于构建菜单树的集合
        List<SysResources> resources = new ArrayList<>();

        if (sysUser.getRoles() == null || sysUser.getRoles().size() == 0) {
            authorities.add((GrantedAuthority) () -> DEFAULT_ROLE);
        } else {
            for (SysRole role : sysUser.getRoles()) {
                if (!role.isDeleted() && role.getStatus() == CommonModel.Status.ACTIVE) {
                    //将角色对应的所有资源添加到用于构建菜单树的集合
                    authorities.add((GrantedAuthority) role::getRoleKey);
                    for (SysResources resource : role.getResources()) {
                        if (!resource.isDeleted() && resource.getStatus() == CommonModel.Status.ACTIVE) {
                            resources.add(resource);
                            authorities.add((GrantedAuthority) resource::getResPath);
                        }
                    }
                }
            }
        }
        return LoginOutput.builder()
                .id(sysUser.getId())
                .username(sysUser.getUsername())
                .nickName(sysUser.getNickName())
                .password(sysUser.getPassword())
                .enabled(sysUser.getStatus() == CommonModel.Status.ACTIVE)
                .accountNonExpired(sysUser.getStatus() == CommonModel.Status.ACTIVE)
                .credentialsNonExpired(sysUser.getStatus() == CommonModel.Status.ACTIVE)
                .accountNonLocked(sysUser.getStatus() != CommonModel.Status.LOCK)
                .authorities(authorities)
                .resources(LoginOutput.buildTree(resources)).build();

    }

}
