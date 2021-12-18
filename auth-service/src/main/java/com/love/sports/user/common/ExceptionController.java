package com.love.sports.user.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    //@ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception e) {
        log.error("system error", e);
        return ResponseEntity.ok(Res.error(ExceptionType.SYSTEM_ERROR.getCode(), e.getMessage()));
    }
}
