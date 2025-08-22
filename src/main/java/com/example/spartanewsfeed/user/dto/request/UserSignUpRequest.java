package com.example.spartanewsfeed.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignUpRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    //?=.*[A-Za-z]) 영문자 최소 1개, (?=.*[0-9]) 숫자 최소 1개, (?=.*[+*~_.-]) 특수문자 최소 1개, [A-Za-z0-9+*~_.-]+ 실제로 매핑할 문자열
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[+*~_.-])[A-Za-z0-9+*~_.-]+$", message = "대소문자 영문, 숫자, 특수문자(+*~_.-)를 최소 1글자 이상 포함해야 합니다.")
    private String password;

}
