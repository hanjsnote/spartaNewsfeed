package com.example.spartanewsfeed.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetResponse {
    private final Long id;
    private final Long userId;
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int likeCount; // 좋아요 갯수

    public GetResponse(Long id,
                       Long userId,
                       String name,
                       String title,
                       String content,
                       LocalDateTime createdAt,
                       LocalDateTime modifiedAt,
                       int likeCount) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
    }
}