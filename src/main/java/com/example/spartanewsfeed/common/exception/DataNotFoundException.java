package com.example.spartanewsfeed.common.exception;

import org.springframework.http.HttpStatus;

// 존재하지 않는 데이터에 접근을 시도할 시 발생하는 예외입니다.
public class DataNotFoundException extends GlobalException{
    public DataNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
