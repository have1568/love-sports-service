package com.love.sports.user.config.impl;

import com.love.sports.outs.GrantedAuthorityOut;
import com.love.sports.outs.LoginOutput;
import com.love.sports.outs.ResourcesOutput;
import com.love.sports.user.entity.model.*;
import com.love.sports.user.repository.*;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    @Resource
    private SysRolesResourcesRepository sysRolesResourcesRepository;

    @Resource
    private SysUsersRolesRepository sysUsersRolesRepository;

    @Resource
    private SysResourcesRepository sysResourcesRepository;



    public static final String DEFAULT_ROLE = "ROLE_USER";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SysUserInfo> userInfos = sysUserInfoRepository.findByUsernameAndDelFlag(username, false);
        SysUserInfo sysUser;

        if (userInfos.isEmpty()) {
            throw new UsernameNotFoundException("用户不存在:" + username);
        } else {
            sysUser = userInfos.get(0);
        }

        //构建只包含角色KEY和资源PATH 的权限集合
        Set<GrantedAuthority> authorities = new HashSet<>();

        //用于构建菜单树的集合
        List<ResourcesOutput> resources = new ArrayList<>();
        //获取到所有的用户角色对象
        Set<SysUsersRoles> sysUserRoles = sysUser.getSysUserRoles();
        Set<SysRole> sysRoles = sysUserRoles.stream().map(SysUsersRoles::getSysRole).collect(Collectors.toSet());
        sysUser.setRoles(sysRoles);
        //如果为空默认角色
        if (sysRoles.isEmpty()) {
            authorities.add(new GrantedAuthorityOut(DEFAULT_ROLE));
        } else {

            //获取到用户对应的资源
            Set<SysResources> sysResources = sysResourcesRepository.findByUserId(sysUser.getId());
            //构建Security User
            for (SysUsersRoles role : sysUserRoles) {
                //设置角色
                authorities.add((new GrantedAuthorityOut(role.getSysRole().getRoleKey())));
            }
            for (SysResources resource : sysResources) {
                authorities.add(new GrantedAuthorityOut(resource.getResPath(), resource.getClientId()));
                ResourcesOutput resourcesOutput = ResourcesOutput.builder()
                        .id(resource.getId())
                        .parentId(resource.getParentId())
                        .resName(resource.getResName())
                        .resIcon(resource.getResIcon())
                        .resPath(resource.getResPath())
                        .httpMethod(resource.getHttpMethod().name())
                        .resType(resource.getResType().name())
                        .root(resource.getRoot())
                        .resSort(resource.getResSort())
                        .clientId(resource.getClientId())
                        .build();
                //设置资源
                resources.add(resourcesOutput);
            }
        }
        return LoginOutput.builder()
                .id(sysUser.getId())
                .username(sysUser.getUsername())
                .nickName(sysUser.getNickName())
                .password(sysUser.getPassword())
                .enabled(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .accountNonExpired(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .credentialsNonExpired(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .accountNonLocked(sysUser.getStatus() != AuditModel.Status.LOCK)
                .authorities(authorities)
                .resources(LoginOutput.buildTree(resources)).build();

    }
}
