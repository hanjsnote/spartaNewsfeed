package com.example.spartanewsfeed.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 모든 커스텀 예외들이 상속받을 GlobalException 입니다.
// 이 클래스는 RuntimeException 을 상속받으며, 상속받는 과정에서 Throwable 을 동시에 상속받습니다. (RuntimeException 이 Throwable을 상속받기 때문)
// 메시지를 받아 저장하기 위해, String message를 사용합니다. 이 메시지 또한 부모 클래스에 존재하는 필드입니다.
// 부모클래스의 생성자에 message를 넣어 생성하고, 이를 2차적으로 HttpStatus와 붙여 원하는 모양으로 클래스를 선언합니다.
@Getter
public class GlobalException extends RuntimeException {

    private HttpStatus httpStatus;

    public GlobalException(HttpStatus httpStatus,  String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
