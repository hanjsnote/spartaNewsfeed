package com.example.spartanewsfeed.comment.dto.response;

// TODO : ("패키지 소실을 막기 위한 파일입니다.")

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ResponseDto {
    private final long id;
    private final long userId;
    private final long postId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ResponseDto(Long id, long userId, long postId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
