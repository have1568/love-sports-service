package com.love.sports.user.service;

import com.alibaba.fastjson.JSON;
import com.love.sports.user.LoveSportsAuthApplicationTests;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SysResourcesServiceTest extends LoveSportsAuthApplicationTests {

    @Resource
    SysResourcesService sysResourcesService;

    @Test
    void findAllForSelect() {
        List<Map<String, Object>> allForSelect = sysResourcesService.findAllForSelect();

        System.out.println(JSON.toJSONString(allForSelect));
    }
}