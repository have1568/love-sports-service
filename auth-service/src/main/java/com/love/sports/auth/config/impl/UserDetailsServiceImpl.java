package com.love.sports.auth.config.impl;

import com.love.sports.auth.entity.model.AuditModel;
import com.love.sports.auth.entity.model.SysRole;
import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.repository.SysUserInfoRepository;
import com.love.sports.outs.GrantedAuthorityOut;
import com.love.sports.outs.LoginOutput;
import com.love.sports.outs.ResourcesOutput;
import com.love.sports.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

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

        //如果为空默认角色
        if (sysUser.getRoles().isEmpty()) {
            authorities.add(new GrantedAuthorityOut(SysRole.DEFAULT_ROLE));
        } else {
            //获取到用户对应的资源
            sysUser.getRoles()
                    .forEach(role -> {
                        //设置角色
                        authorities.add((new GrantedAuthorityOut(role.getRoleKey())));
                        role.getResources().forEach(resource -> {
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
                        });
                    });

        }
        return LoginOutput.builder()
                .id(sysUser.getId())
                .username(sysUser.getUsername())
                .nickName(sysUser.getNickName())
                .roleLevel(getUserRoleLevel(sysUser.getRoles()))
                .password(sysUser.getPassword())
                .enabled(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .accountNonExpired(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .credentialsNonExpired(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .accountNonLocked(sysUser.getStatus() != AuditModel.Status.LOCK)
                .authorities(authorities)
                .resources(TreeUtils.buildTree(resources)).build();
    }

    private Integer getUserRoleLevel(Set<SysRole> userRoles) {
        return userRoles.stream().min(Comparator.comparing(SysRole::getRoleLevel)).get().getRoleLevel();

    }
}
