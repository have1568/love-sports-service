package com.love.sports.auth.service;

import com.love.sports.auth.entity.model.SysDept;
import com.love.sports.auth.repository.SysDeptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Service
public class SysDeptService {

    @Resource
    private SysDeptRepository sysDeptRepository;


    @Transactional
    public SysDept save(SysDept sysDept) {
        return sysDeptRepository.save(sysDept);
    }


    @Transactional
    public boolean deleteById(Integer id) {
        Optional<SysDept> optional = sysDeptRepository.findById(id);
        if (optional.isPresent()) {
            SysDept sysDept = optional.get();
            sysDept.setDelFlag(true);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean update(SysDept sysDept, int id) {
        SysDept saved = findById(id);
        Assert.notNull(saved, "更新数据不存在");
        Assert.notNull(sysDept, "提交数据为空");
        BeanUtils.copyProperties(sysDept, saved);
        sysDeptRepository.save(saved);
        return true;
    }

    public SysDept findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysDeptRepository.findById(id).orElse(null);
    }

    public Page<SysDept> findByCondition(SysDept sysDept, Pageable pageable) {
        if (sysDept == null) {
            return sysDeptRepository.findAll(pageable);
        }
        Example<SysDept> example = Example.of(sysDept);
        return sysDeptRepository.findAll( pageable);
    }


}
