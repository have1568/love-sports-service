package com.love.sports.auth.config.handler;

import com.alibaba.fastjson.JSON;
import com.love.sports.auth.common.ExceptionType;
import com.love.sports.auth.common.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter out = response.getWriter();
        if (exception instanceof LockedException) {
            out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "账户被锁定!")));
        } else if (exception instanceof CredentialsExpiredException) {
            out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "密码过期!")));
        } else if (exception instanceof AccountExpiredException) {
            out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "账户过期!")));
        } else if (exception instanceof DisabledException) {
            out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "账户被禁用!")));
        } else if (exception instanceof BadCredentialsException) {
            out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "用户名或者密码输入错误，请重新输入!")));
        } else {
            out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), ExceptionType.AUTH_ERROR.getMessage())));
        }

        out.flush();
        out.close();
    }
}
