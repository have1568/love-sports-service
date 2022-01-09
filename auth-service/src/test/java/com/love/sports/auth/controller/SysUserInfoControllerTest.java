package com.love.sports.auth.controller;

import com.alibaba.fastjson.JSON;
import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.auth.entity.model.SysUserInfo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SysUserInfoControllerTest extends LoveSportsAuthApplicationTests {

    public static final String USER_ID = "1";

    @Order(0)
    @Test
    @SneakyThrows
    void pageQuery() {
        final MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("page", 0);
        params.add("size", 10);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(get("/api/user/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
    }

    @Order(1)
    @SneakyThrows
    @Test
    void findById() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(get("/api/user/get/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
    }

    @Order(2)
    @SneakyThrows
    @Test
    void save() {
        SysUserInfo user = SysUserInfo.builder()
                .username("TEST_SAVE")
                .password(PASSWORD)
                .nickName("TEST_SAVE")
                .email("1006369654@qq.com")
                .phoneNumber("13991506977")
                .sex(SysUserInfo.Sex.MALE)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(user))
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Order(3)
    @SneakyThrows
    @Test
    void update() {
        SysUserInfo user = SysUserInfo.builder()
                .username("TEST_UPDATE")
                .password(PASSWORD)
                .nickName("TEST_UPDATE")
                .email("1006369654@qq.com")
                .phoneNumber("13991506977")
                .sex(SysUserInfo.Sex.MALE)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/user/update/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(user))
                        .headers(headers))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Order(4)
    @SneakyThrows
    @Test
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        MvcResult mvcResult = mockMvc.perform(post("/api/user/delete/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers))
                .andExpect(status().isOk())
                .andReturn();
    }


}