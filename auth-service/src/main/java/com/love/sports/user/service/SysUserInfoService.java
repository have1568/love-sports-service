package com.love.sports.user.service;


import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.entity.model.SysUsersRoles;
import com.love.sports.user.repository.SysUserInfoRepository;
import com.love.sports.user.repository.SysUsersRolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * 业务层
 *
 * @author makejava
 * @since 2021-12-05 17:59:18
 */
@Slf4j
@Service
public class SysUserInfoService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysUsersRolesRepository sysUsersRolesRepository;

    @Transactional
    public SysUserInfo save(SysUserInfo sysUserInfo) {
        sysUserInfo.setPassword(passwordEncoder.encode(sysUserInfo.getPassword()));
        maintainRoleRef(sysUserInfo);
        return sysUserInfoRepository.save(sysUserInfo);
    }


    @Transactional
    public boolean deleteById(String id) {
        Optional<SysUserInfo> optional = sysUserInfoRepository.findById(id);
        if (optional.isPresent()) {
            SysUserInfo entity = optional.get();
            entity.setDelFlag(true);
            return true;
        }
        return false;
    }

    public void delete(String id) {
        sysUserInfoRepository.deleteById(id);
    }

    @Transactional
    public boolean update(SysUserInfo sysUserInfo, String id) {
        Assert.notNull(sysUserInfo, "数据不存在");
        boolean exist = sysUserInfoRepository.existsById(id);
        if (!exist) {
            return false;
        }
        maintainRoleRef(sysUserInfo);
        save(sysUserInfo);
        return true;
    }

    public SysUserInfo findById(String id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysUserInfoRepository.findById(id).orElse(null);
    }


    public Page<SysUserInfo> findByCondition(SysUserInfo sysUserInfo, Pageable page) {

        Example<SysUserInfo> example = Example.of(sysUserInfo);
        return sysUserInfoRepository.findAll(page);
    }

    //维护用户角色关联表
    @Transactional
    public void maintainRoleRef(SysUserInfo sysUserInfo) {
        //拿到已存储的用户角色
        Set<SysUsersRoles> storeUsersRoles = sysUsersRolesRepository.findBySysUserInfo(sysUserInfo);

        //暂存已保存的用户角色
        Set<SysUsersRoles> storeUsersRolesTemp = new HashSet<>(storeUsersRoles);

        Set<SysUsersRoles> inputUserRoles = sysUserInfo.getSysUserRoles();

        //找出已保存的但是输入的没有的元素 删除这些元素
        boolean hasOldEl = storeUsersRoles.removeAll(inputUserRoles);
        if (hasOldEl) {
            sysUsersRolesRepository.deleteAll(storeUsersRoles);
        }

        //找出输入的元素里有但是已保存的没有的元素 保存这些元素
        boolean hasNewEl = inputUserRoles.removeAll(storeUsersRolesTemp);
        if(hasNewEl){
            sysUsersRolesRepository.saveAll(inputUserRoles);
        }

    }
}

