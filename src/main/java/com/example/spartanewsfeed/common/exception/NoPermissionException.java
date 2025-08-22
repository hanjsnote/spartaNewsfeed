package com.example.spartanewsfeed.common.exception;

import org.springframework.http.HttpStatus;

// 권한이 없는 메서드를 요청했을 때 발생하는 예외입니다.
public class NoPermissionException extends GlobalException{
    public NoPermissionException(String message){
        super(HttpStatus.FORBIDDEN, message);
    }
}
