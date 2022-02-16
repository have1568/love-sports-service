package com.love.sports.file.controller;

import com.love.sports.file.exception.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({FileException.class, MultipartException.class})
    public ResponseEntity<String> fileError(Exception e) {
        log.error("file error:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file error");
    }
}
