package com.love.sports.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.love.sports.auth.entity.model.SysClientDetail;
import com.love.sports.auth.service.SysClientDetailService;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(JUnitPlatform.class)
public class LoveSportsAuthApplicationTests {

    public static final String CLIENT_ID = "test_client";
    public static final String CLIENT_SECRET = "password";
    public static final String USERNAME = "have1568";
    public static final String PASSWORD = "123456";
    public static final String GRANT_TYPE = "password";
    public static final String SCOPE = "all";

    @Resource
    protected MockMvc mockMvc;


    public static BasicAuthenticationRequestPostProcessor basicAuth(String username, String password) {
        return new BasicAuthenticationRequestPostProcessor(username, password);
    }


    private static final class BasicAuthenticationRequestPostProcessor implements RequestPostProcessor {

        private final String username;

        private final String password;

        private BasicAuthenticationRequestPostProcessor(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(this.username, this.password);
            request.addHeader("Authorization", headers.getFirst("Authorization"));
            return request;
        }

    }


    @Resource
    private SysClientDetailService sysClientDetailService;

    /**
     *
     */
    //@Test
    void contextLoads() {

        SysClientDetail clientDetail = SysClientDetail.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scope("all")
                .autoApprove("all")
                .authorizedGrantTypes("authorization_code,password,refresh_token,client_credentials,implicit")
                .refreshTokenValidity(12800)
                .accessTokenValidity(6400)
                .authorities("ROLE_TRUSTED_CLIENT")
                .webServerRedirectUri("http://127.0.0.1:8080/authorized")
                .build();

        sysClientDetailService.save(clientDetail);

    }

    public String getToken() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("scope", SCOPE);
        params.add("username", USERNAME);
        params.add("password", PASSWORD);
        MvcResult mvcResult = mockMvc.perform(post("/oauth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params)
                        .with(basicAuth(CLIENT_ID, CLIENT_SECRET)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists()).andReturn();

        JSONObject jsonObject = JSON.parseObject(mvcResult.getResponse().getContentAsString());
        return jsonObject.getString("access_token");
    }

}
