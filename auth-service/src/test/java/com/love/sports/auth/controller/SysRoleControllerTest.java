package com.love.sports.auth.controller;

import com.alibaba.fastjson.JSON;
import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.auth.entity.model.SysRole;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SysRoleControllerTest extends LoveSportsAuthApplicationTests {

    public static final int ROLE_ID = 2;

    @Test
    @Order(1)
    @SneakyThrows
    void save() {

        SysRole role = SysRole.builder()
                .roleKey("ROLE_TEST")
                .roleName("测试角色")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/role/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(role))
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
        MvcResult mvcResult = mockMvc.perform(get("/api/role/get/" + ROLE_ID)
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
        MvcResult mvcResult = mockMvc.perform(get("/api/role/list")
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
        SysRole role = SysRole.builder()
                .roleKey("ROLE_TEST_UPDATE")
                .roleName("测试修改角色")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/role/update/" + ROLE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(role))
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
        MvcResult mvcResult = mockMvc.perform(post("/api/role/delete/" + ROLE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

}