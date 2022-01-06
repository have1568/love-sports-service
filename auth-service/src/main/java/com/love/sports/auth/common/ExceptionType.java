package com.love.sports.auth.common;

public enum ExceptionType {

    SYSTEM_ERROR(500,"系统错误"),
    AUTH_ERROR(401,"认证失败"),
    AUTHORITY_ERROR(403,"权限错误");


    private int code;
    private String message;

    ExceptionType(int i, String msg) {
        this.code = i;
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
