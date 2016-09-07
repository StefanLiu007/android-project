package com.example.stefan.rxjava.http;

/**
 * Created by Stefan on 2016/7/21.
 */
public class ApiException extends RuntimeException{
    public static final int USER_NOT_EXIST = 100;
    public static final int PASSWORD_ERROR = 101;

    public ApiException(int resultCode) {

    }

    private static String getApiException(int code){
        String message = "";
        switch (code){
            case USER_NOT_EXIST:
                message = "用户不存在";
                break;
            case PASSWORD_ERROR:
                message = "密码错误";
                break;
                default:
                    message = "未知错误";
        }
        return message;
    }
}
