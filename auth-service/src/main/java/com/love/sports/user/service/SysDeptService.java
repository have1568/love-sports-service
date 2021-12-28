package com.love.sports.user.service;

import com.love.sports.user.entity.model.SysDept;
import com.love.sports.user.repository.SysDeptRepository;
import lombok.extern.slf4j.Slf4j;
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
        Assert.notNull(sysDept, "数据不存在");
        boolean exist = sysDeptRepository.existsById(id);
        if (!exist) {
            return false;
        }
        sysDeptRepository.save(sysDept);
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
