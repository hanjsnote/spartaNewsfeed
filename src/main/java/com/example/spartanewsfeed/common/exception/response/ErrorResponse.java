package com.example.spartanewsfeed.common.exception.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

// 에러가 발생하면 return 할 응답 객체입니다.
// 이 객체는 생성자를 통해 HttpStatus 와 오류 매시지, 오류가 발생한 엔드포인트를 파라미터로 받습니다.

@Getter
public class ErrorResponse {

    // 에러 코드를 나타내는 정수값 status 입니다.
    private final int status;

    // 에러 유형을 나타내는 문자열 error 입니다. (예: FORBIDDEN)
    private final String error;

    private final String message; // 오류메시지
    private final String path; // 오류가 발생한 경로
    private final LocalDateTime timestamp; // 오류발생날짜

    public ErrorResponse(HttpStatus status, String message, String path){
        this.status = status.value();
        this.error = status.name();
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
