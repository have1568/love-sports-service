package com.love.sports.auth.web;

import com.love.sports.auth.entity.model.SysClientDetail;
import com.love.sports.auth.service.SysClientDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
public class AuthPageController {


    @Resource
    private SysClientDetailService sysClientDetailService;

    @ResponseBody
    @PostMapping("/oauth/confirm_access_info")
    public AuthorizationRequest getAccessConfirmation(@ModelAttribute("authorizationRequest") AuthorizationRequest authorizationRequest) {
        SysClientDetail clientDetail = sysClientDetailService.findById(authorizationRequest.getClientId());
        Map<String, Serializable> info = new HashMap<>();
        info.put("clientDetail", clientDetail);
        authorizationRequest.setExtensions(info);
        return authorizationRequest;
    }


    @GetMapping("/oauth/confirm_access")
    public void getAccessPage(HttpServletResponse response) throws IOException {
        log.info("授权页面");
        response.sendRedirect("/#/authorization");
    }
}
