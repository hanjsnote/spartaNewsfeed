package com.example.spartanewsfeed.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequest {
    @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
    @Size(min = 1, max = 200, message = "댓글은 1자 이상 200자 이하로 입력해주세요.")
    private String content;
}
