package com.love.sports.user.config.handdler;

import lombok.extern.log4j.Log4j2;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String s = new ObjectMapper().writeValueAsString(authentication);
        log.info("Login Success : {}",s);
    }
}
