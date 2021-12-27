package com.love.sports.user.controller;

import com.alibaba.fastjson.JSON;
import com.love.sports.user.LoveSportsAuthApplicationTests;
import com.love.sports.user.entity.model.SysResources;
import com.love.sports.user.entity.model.SysRole;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Rollback
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SysResourcesControllerTest extends LoveSportsAuthApplicationTests {

    public static final int RESOURCE_ID = 3;

    @Test
    @Order(1)
    @SneakyThrows
    void save() {


        SysResources resource = SysResources.builder()
                .httpMethod(HttpMethod.GET)
                .parentId(3)
                .resPath("/api/resources/delete/**")
                .resName("资源管理-删除")
                .resIcon("fa fa-menu")
                .resSort(11)
                .resType(SysResources.ResourceType.API)
                .root(false)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/resources/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(resource))
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(2)
    @SneakyThrows
    void findById() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(get("/api/resources/get/" + RESOURCE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
    }


    @Test
    @Order(3)
    @SneakyThrows
    void pageQuery() {
        final MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("page", 0);
        params.add("size", 10);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(get("/api/resources/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
    }


    @Test
    @Order(4)
    @SneakyThrows
    void update() {
        SysResources resource = SysResources.builder()
                .httpMethod(HttpMethod.GET)
                .parentId(0)
                .resPath("/api/resources")
                .resName("资源管理-TEST_UPDATE")
                .resIcon("fa fa-menu")
                .resSort(11)
                .resType(SysResources.ResourceType.MENU)
                .root(true)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/resources/update/" + RESOURCE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(resource))
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(5)
    @SneakyThrows
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/resources/delete/" + RESOURCE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
    }
}