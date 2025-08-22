package com.example.spartanewsfeed.comment.dto.response;

import lombok.Getter ;
import java.time.LocalDateTime;

@Getter
public class PostCommentResponse {
    private final long id;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostCommentResponse(Long id, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
