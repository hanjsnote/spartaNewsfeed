package com.example.spartanewsfeed.common.exception;

import org.springframework.http.HttpStatus;

// 인증에 실패했을 때 발생하는 예외입니다. (로그인 실패 등)
public class InvalidCredentialException extends GlobalException{
    public InvalidCredentialException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
