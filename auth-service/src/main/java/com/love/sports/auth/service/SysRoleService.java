package com.love.sports.auth.service;


import com.love.sports.auth.entity.model.SysResources;
import com.love.sports.auth.entity.model.SysRole;
import com.love.sports.auth.repository.SysResourcesRepository;
import com.love.sports.auth.repository.SysRoleRepository;
import com.love.sports.outs.LoginOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 角色表业务层
 *
 * @author makejava
 * @since 2021-12-05 17:48:53
 */
@Slf4j
@Service
public class SysRoleService {

    @Resource
    private SysRoleRepository sysRoleRepository;


    @Resource
    private SysResourcesRepository sysResourcesRepository;

    @Transactional
    public SysRole save(SysRole sysRole) {
        List<SysResources> sysResources = getAndVerifyResources(sysRole);
        sysRole.setResources(new HashSet<>(sysResources));
        return sysRoleRepository.save(sysRole);
    }


    @Transactional
    public boolean deleteById(Integer id) {
        Optional<SysRole> optional = sysRoleRepository.findById(id);
        if (optional.isPresent()) {
            SysRole entity = optional.get();
            entity.setDelFlag(true);
            return true;
        }
        return false;
    }

    public void delete(Integer id) {
        sysRoleRepository.deleteById(id);
    }

    @Transactional
    public boolean update(SysRole sysRole, Integer id) {
        SysRole saved = findById(id);
        Assert.notNull(saved, "更新数据不存在");
        Assert.notNull(sysRole, "提交数据为空");
        BeanUtils.copyProperties(sysRole, saved);
        List<SysResources> sysResources = getAndVerifyResources(sysRole);
        saved.setResources(new HashSet<>(sysResources));
        sysRoleRepository.save(saved);
        return true;
    }

    public SysRole findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysRoleRepository.findById(id).orElse(null);
    }

    public SysRole getAdminRole() {
        SysRole admin = sysRoleRepository.findByRoleKey(SysRole.DEFAULT_ADMIN);
        if (admin == null) {
            SysRole sysRole = SysRole.builder()
                    .roleName("系统管理员")
                    .roleKey(SysRole.DEFAULT_ADMIN)
                    .roleLevel(0)
                    .build();
            List<SysResources> allResources = sysResourcesRepository.findAll();
            sysRole.setResources(new HashSet<>(allResources));
            return sysRoleRepository.save(sysRole);
        } else {
            return admin;
        }
    }

    public SysRole getDefaultRole() {
        SysRole defaultRole = sysRoleRepository.findByRoleKey(SysRole.DEFAULT_ROLE);
        if (defaultRole == null) {
            SysRole sysRole = SysRole.builder()
                    .roleName("普通用户")
                    .roleKey(SysRole.DEFAULT_ROLE)
                    .roleLevel(2)
                    .build();
            List<SysResources> allResources = sysResourcesRepository.findAll();
            sysRole.setResources(new HashSet<>(allResources));
            return sysRoleRepository.save(sysRole);
        } else {
            return defaultRole;
        }
    }

    public Page<SysRole> findByCondition(Pageable page) {
        Integer roleLevel = ((LoginOutput) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRoleLevel();
        return sysRoleRepository.findByRoleLevelGreaterThanEqual(roleLevel, page);
    }

    private List<SysResources> getAndVerifyResources(SysRole sysRole) {
        Set<Integer> ids = sysRole.getResources().stream().map(SysResources::getId).collect(Collectors.toSet());
        List<SysResources> sysResources = sysResourcesRepository.findAllById(ids);

        if (sysResources.size() < sysRole.getResources().size()) {
            throw new IllegalArgumentException("资源不存在");
        }
        return sysResources;
    }


}

