package com.love.sports.user.service;


import java.lang.Integer;

import com.love.sports.user.entity.model.SysResources;
import com.love.sports.user.repository.SysResourcesRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 业务层
 *
 * @author makejava
 * @since 2021-12-05 17:56:05
 */
@Slf4j
@Service
public class SysResourcesService {

    @Resource
    private SysResourcesRepository sysResourcesRepository;

    @Transactional
    public SysResources save(SysResources sysResources) {
        return sysResourcesRepository.save(sysResources);
    }


    @Transactional
    public boolean deleteById(Integer id) {
        Optional<SysResources> optional = sysResourcesRepository.findById(id);
        if (optional.isPresent()) {
            SysResources entity = optional.get();
            entity.setDeleted(true);
            return true;
        }
        return false;
    }

    public void delete(Integer id) {
        sysResourcesRepository.deleteById(id);
    }

    @Transactional
    public boolean update(SysResources sysResources, Integer id) {
        Assert.notNull(sysResources, "数据不存在");
        boolean exist = sysResourcesRepository.existsById(id);
        if (!exist) {
            return false;
        }
        sysResourcesRepository.save(sysResources);
        return true;
    }

    public SysResources findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysResourcesRepository.findById(id).orElse(null);
    }


    public Page<SysResources> findByCondition(SysResources sysResources, Pageable page) {
        if (sysResources == null) {
            return sysResourcesRepository.findAll(page);
        }
        Example<SysResources> example = Example.of(sysResources);
        return sysResourcesRepository.findAll(example, page);
    }

    public List<Map<String, Object>> findAllForSelect() {
        return sysResourcesRepository.findAllForSelect();
    }

}

