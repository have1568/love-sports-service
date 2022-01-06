package com.love.sports.auth.controller;

import com.alibaba.fastjson.JSON;
import com.love.sports.auth.LoveSportsAuthApplicationTests;
import com.love.sports.auth.config.constant.Whitelist;
import com.love.sports.auth.entity.model.SysUserInfo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@Rollback
class AuthControllerTest extends LoveSportsAuthApplicationTests {


    /**
     * {
     * "username": "have1568",
     * "password": "123456",
     * "nickName": "wangxingzhu",
     * "email": "1006369654@qq.com",
     * "phoneNumber": "13991506977",
     * "sex": "MALE"
     * }
     */
    @Test
    void register() throws Exception {
        SysUserInfo user = SysUserInfo.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .nickName("wang")
                .email("1006369654@qq.com")
                .phoneNumber("13991506977")
                .sex(SysUserInfo.Sex.MALE)
                .build();
        MvcResult mvcResult = mockMvc.perform(post(Whitelist.REGISTER_PROCESSING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(user)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void login() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", USERNAME);
        params.add("password", PASSWORD);
        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params))
                .andExpect(status().isOk()).andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
    }

    @Test
    void token() throws Exception {
        getToken();
    }

    @Test
    void checkToken() throws Exception {

        String token = getToken();

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        MvcResult mvcResult = mockMvc.perform(post("/oauth/check_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params))
                .andExpect(status().isOk())
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.println(mvcResult.getResponse().getContentAsString());

    }

    @Test
    @SneakyThrows
    void userInfo() {
        String token = getToken();

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        MvcResult mvcResult = mockMvc.perform(post("/auth/userinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.println(mvcResult.getResponse().getContentAsString());
    }


}