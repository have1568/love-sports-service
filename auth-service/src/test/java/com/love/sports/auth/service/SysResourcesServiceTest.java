package com.love.sports.auth.service;

import com.alibaba.fastjson.JSON;
import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.auth.entity.model.SysResources;
import com.love.sports.utils.TreeUtils;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Collection;

class SysResourcesServiceTest extends LoveSportsAuthApplicationTests {

    @Resource
    SysResourcesService sysResourcesService;

    @Test
    void findAllForSelect() {
        Collection<SysResources> allForSelect = sysResourcesService.findAllForSelect();

        System.out.println(JSON.toJSONString(allForSelect));
    }

    @Test
    void findAllCurrentUserAllowResourcesTest() {
        Collection<SysResources> resources = sysResourcesService.currentUserAllowTreeResources("1");
        Collection<SysResources> tree = TreeUtils.buildTree(resources);
        System.out.println(tree);
    }
}