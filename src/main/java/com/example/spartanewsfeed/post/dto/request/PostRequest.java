package com.example.spartanewsfeed.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequest {

    @NotBlank(message = "제목을 입력해 주세요!")
    @Size(min = 1, max = 30, message = "게시글 제목은 1자 이상 30자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요!")
    @Size(min = 1, max = 200, message = "게시글 내용은 1자 이상 200자 이하로 입력해주세요.")
    private String content;
}
