package com.love.sports.user.service;


import java.lang.Integer;
import com.love.sports.user.entity.model.SysRole;
import com.love.sports.user.repository.SysRoleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;


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

    @Transactional
    public SysRole save(SysRole sysRole) {
       return sysRoleRepository.save(sysRole);
    }

 
    @Transactional
    public boolean deleteById(Integer id) {
        Optional<SysRole> optional = sysRoleRepository.findById(id);
        if (optional.isPresent()) {
            SysRole entity= optional.get();
            entity.setDeleted(true);
            return true;
        }
        return false;
    }
    
    public void delete(Integer id) {
        sysRoleRepository.deleteById(id);
    }

    @Transactional
    public boolean update(SysRole sysRole, Integer id) {
        Assert.notNull(sysRole, "数据不存在");
        boolean exist = sysRoleRepository.existsById(id);
        if (!exist) {
            return false;
        }
        sysRoleRepository.save(sysRole);
        return true;
    }

    public SysRole findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysRoleRepository.findById(id).orElse(null);
    }
 

    public Page<SysRole> findByCondition(SysRole sysRole,Pageable page) {
        if (sysRole == null) {
            return sysRoleRepository.findAll(page);
        }
        Example<SysRole> example = Example.of( sysRole);
        return sysRoleRepository.findAll(example,page);
    }
}

