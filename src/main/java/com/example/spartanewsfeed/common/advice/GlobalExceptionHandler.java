package com.example.spartanewsfeed.common.advice;

import com.example.spartanewsfeed.common.exception.DataNotFoundException;
import com.example.spartanewsfeed.common.exception.GlobalException;
import com.example.spartanewsfeed.common.exception.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import java.util.stream.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // GlobalException을 상속받은 예외가 발생하면 아래의 핸들러가 작동됩니다.
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handlerGlobalException(GlobalException ex, HttpServletRequest request) {
        HttpStatus status = ex.getHttpStatus();
        ErrorResponse response = new ErrorResponse(
                status,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }

    // request로 바인딩 할 때 Valid 제약에 어긋난다면 기본적으로 발생하는 예외입니다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // 예외에 담긴 정보와 request를 받습니다. (Path값, 오류가 난 값을 return 하기 위함)
    public ResponseEntity<ErrorResponse> handlerValidationException(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest request) {
        // 제약에 어긋난 필드 중, 가장 첫번째 것을 찾습니다.
        // 제약에서 (massage = "이메일은 비어있을 수 없습니다.") 와 같이 작성되어 있고,
        // request로 바인딩 될 때 Validation에 어긋나 있고, 이 필드가 가장 처음의 것이라면 그 메시지를 저장합니다.
        String firstErrorMassage = ex.getBindingResult()
                .getFieldErrors() // 검증에 실패한 필드들의 리스트를 만듭니다.
                .stream()
                .findFirst() // 첫 번째 요소를 찾습니다. 이는 Optional<FieldError>의 형태를 가집니다.
                .map(DefaultMessageSourceResolvable::getDefaultMessage) // FieldError의 메시지를 저장합니다.
                // 해당 오류가 발생하였으나, 가장 첫 번째 것을 찾을 수 없다면. (메시지가 비어있거나, 또 다른 오류로 인해 메시지를 찾을 수 없다면)
                // 오류를 던집니다.
                .orElseThrow(() -> new IllegalArgumentException("검증 에러가 반드시 존재해야 합니다."));

        // return 해주기 위해, ErrorResponse를 만듭니다.
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST, // 400 BAD_REQUEST
                firstErrorMassage,      // 위에서 찾아낸 에러 메시지
                request.getRequestURI() // 해당 오류가 발생한 URI
        );

        // 에러 코드와 함께, ErrorResponse를 return 합니다.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
