package com.love.sports.user.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

@Slf4j
public class WebResponseExceptionTranslatorImpl extends DefaultWebResponseExceptionTranslator {


    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        log.error("error", e);
        return super.translate(e);
    }
}
