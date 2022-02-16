package com.love.sports.file.exception;

/**
 * @author WangXinzhu
 * @date 2022/2/13 14:41
 * @since 1.0
 */
public class FileException extends RuntimeException{
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}