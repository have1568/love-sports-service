package com.love.sports.auth.service;


import com.love.sports.auth.config.constant.RedisCacheNameConstant;
import com.love.sports.auth.entity.model.SysResources;
import com.love.sports.auth.entity.model.SysRole;
import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.repository.SysResourcesRepository;
import com.love.sports.auth.repository.SysUserInfoRepository;
import com.love.sports.outs.ResourcesOutput;
import com.love.sports.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;


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

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    @Resource
    private SysRoleService sysRoleService;

    @Transactional
    public SysResources save(SysResources sysResources) {
        SysResources saved = sysResourcesRepository.save(sysResources);

        //每次添加资源自动保存到管理员的权限列表里
        SysRole adminRole = sysRoleService.getAdminRole();
        adminRole.getResources().add(saved);
        sysRoleService.save(adminRole);

        return saved;
    }


    @Transactional
    public boolean deleteById(Integer id) {
        Optional<SysResources> optional = sysResourcesRepository.findById(id);
        if (optional.isPresent()) {
            SysResources entity = optional.get();
            entity.setDelFlag(true);
            return true;
        }
        return false;
    }

    public void delete(Integer id) {
        sysResourcesRepository.deleteById(id);
    }

    /**
     * 更新资源，同时将所有的角色缓存删除
     *
     * @param sysResources 输入的资源
     * @param id           要更新的资源ID
     * @return 是否更新成功
     */
    @Transactional
    @CacheEvict(cacheNames = RedisCacheNameConstant.ROLES, allEntries = true, cacheManager = "sysCacheManager")
    public boolean update(SysResources sysResources, Integer id) {
        SysResources saved = findById(id);
        Assert.notNull(saved, "更新数据不存在");
        Assert.notNull(sysResources, "提交数据为空");
        BeanUtils.copyProperties(sysResources, saved);
        sysResourcesRepository.save(saved);
        return true;
    }

    public SysResources findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysResourcesRepository.findById(id).orElse(null);
    }


    public Page<SysResources> findByCondition(SysResources sysResources, Pageable page) {
        ExampleMatcher.GenericPropertyMatcher contains = ExampleMatcher.GenericPropertyMatchers.contains();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("resName", contains)
                .withMatcher("resPath", contains);
        return sysResourcesRepository.findAll(Example.of(sysResources, matcher), page);
    }

    public Collection<SysResources> findAllForSelect() {
        return sysResourcesRepository.findAll();
    }


    public Collection<SysResources> currentUserAllowTreeResources(String userId) {
        return TreeUtils.buildTree(findAllCurrentUserAllowResources(userId));
    }

    public Set<SysResources> findAllCurrentUserAllowResources(String userId) {
        SysUserInfo sysUserInfo = sysUserInfoRepository.findById(userId).orElse(null);
        Assert.notNull(sysUserInfo, "用户不存在");
        return sysUserInfo.getRoles().stream().map(SysRole::getResources).findAny().orElse(null);
    }


    /**
     * 获取当前用户菜单树
     *
     * @param userId 用户ID
     * @return
     */
    public Collection<ResourcesOutput> currentUserMenuTree(String userId) {
        SysUserInfo sysUserInfo = sysUserInfoRepository.findById(userId).orElse(null);
        Assert.notNull(sysUserInfo, "用户不存在");
        Set<SysResources> userMenus = sysResourcesRepository.findUserMenus(userId);

        List<ResourcesOutput> outs = new ArrayList<>(userMenus.size());

        userMenus.forEach(resource -> {
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

            outs.add(resourcesOutput);
        });
        return TreeUtils.buildTree(outs);
    }
}

