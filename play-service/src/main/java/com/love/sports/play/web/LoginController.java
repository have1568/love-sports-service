package com.love.sports.play.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author WangXinzhu
 * @date 2022/2/17 17:17
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String GRANT_TYPE = "grant_type";
    public static final String SCOPE = "SCOPE";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public Object login(@RequestBody Map<String, String> loginInput) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String username = loginInput.get(USERNAME);
        String password = loginInput.get(PASSWORD);
        params.add(USERNAME, username);
        params.add(PASSWORD, password);
        params.add(CLIENT_ID, "love-sports-miniapp");
        params.add(CLIENT_SECRET, "123456");
        params.add(GRANT_TYPE, "password");
        params.add(SCOPE, "userinfo role resources");
        Map<String, Object> resp = restTemplate.postForObject("http://localhost:8081/oauth/token", params, Map.class);
        log.info("login res : {}", resp);
        return resp;

    }
}
