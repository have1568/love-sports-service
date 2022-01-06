package com.love.sports.auth.config.handler;

import com.alibaba.fastjson.JSON;
import com.love.sports.auth.entity.model.SysClientDetail;
import com.love.sports.auth.service.SysClientDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Resource
    private SysClientDetailService sysClientDetailService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = "";
        Map<String, Object> res = new HashMap<>();
        res.put("principal", authentication.getPrincipal());
        //没有重定向的页面使用默认的重新向特面
        if (savedRequest != null && StringUtils.hasText(savedRequest.getRedirectUrl())) {
            clearAuthenticationAttributes(request);

            // Use the DefaultSavedRequest URL
            targetUrl = savedRequest.getRedirectUrl();
            res.put("targetUrl", targetUrl);
            log.info("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        } else {
            List<SysClientDetail> apps = sysClientDetailService.findAllClients();
            res.put("apps", apps);
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(res));
        out.flush();
        out.close();
    }
}
