package com.love.sports.user.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Res {

    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 500;

    private final int code;
    private final Object data;
    private final String message;

    public static Object success(Object data){
       return Res.builder().code(SUCCESS_CODE).data(data).message(SUCCESS_MESSAGE).build();
    }

    public static Object error(int code,String message){
        return Res.builder().code(code).message(message).build();
    }

}
