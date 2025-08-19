package com.example.spartanewsfeed.comment.dto.request;

// TODO : ("패키지 소실을 막기 위한 파일입니다.")

import lombok.Getter;

@Getter
public class RequestDto {
    // @NotBlank(message = "댓글 내용은 필수 입력값")
    private String content;
}
