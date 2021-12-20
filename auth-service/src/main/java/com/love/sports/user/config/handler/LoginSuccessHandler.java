package com.love.sports.user.config.handler;

import com.alibaba.fastjson.JSON;
import com.love.sports.user.common.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = "";
        //没有重定向的页面使用默认的重新向特面
        if (savedRequest != null && StringUtils.hasText(savedRequest.getRedirectUrl())) {
            clearAuthenticationAttributes(request);

            // Use the DefaultSavedRequest URL
            targetUrl = savedRequest.getRedirectUrl();
            log.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("targetUrl",targetUrl);
        res.put("principal",authentication.getPrincipal());
        res.put("apps",new ArrayList<>());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(Res.success(res)));
        out.flush();
        out.close();


        //getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
