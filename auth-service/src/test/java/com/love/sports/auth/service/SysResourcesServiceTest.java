package com.love.sports.auth.service;

import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.auth.entity.model.SysResources;
import com.love.sports.outs.ResourcesOutput;
import com.love.sports.utils.TreeUtils;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Collection;

public class SysResourcesServiceTest extends LoveSportsAuthApplicationTests {

    @Resource
    SysResourcesService sysResourcesService;

    @Test
    void findAllForSelect() {
        sysResourcesService.findAllForSelect();
    }

    @Test
    void findAllCurrentUserAllowResourcesTest() {
        Collection<SysResources> resources = sysResourcesService.currentUserAllowTreeResources("1");
        Collection<SysResources> tree = TreeUtils.buildTree(resources);
    }

    @Test
    void currentUserMenuTree() {
        Collection<ResourcesOutput> resources = sysResourcesService.currentUserMenuTree("1");
        System.out.println(resources);
    }
}