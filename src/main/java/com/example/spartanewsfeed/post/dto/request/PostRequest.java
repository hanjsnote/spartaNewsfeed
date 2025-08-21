package com.example.spartanewsfeed.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequest {
    @NotBlank(message = "제목을 입력해 주세요!")
    private String title;
    @NotBlank(message = "내용을 입력해 주세요!")
    private String content;
}
