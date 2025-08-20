package com.example.spartanewsfeed.user.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
//    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "유효한 이메일 형식이 아닙니다.") //이메일의 로컬파트+@도메인 부분
    private final String email;
    private final String password;

    UserLoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
