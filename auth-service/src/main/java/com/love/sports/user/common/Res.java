package com.love.sports.user.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Res<T> {

    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 500;

    private final int code;
    private final T data;
    private final String message;

    public static <T> Res<T> success(){
        return Res.<T>builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).build();
    }
    public static <T> Res<T> success(T data){
        return Res.<T>builder().code(SUCCESS_CODE).data(data).message(SUCCESS_MESSAGE).build();
    }

    public static <T> Res<T> success(String message){
        return Res.<T>builder().code(SUCCESS_CODE).message(message).build();
    }

    public static <T> Res<T> error(int code,String message){
        return Res.<T>builder().code(code).message(message).build();
    }

}
