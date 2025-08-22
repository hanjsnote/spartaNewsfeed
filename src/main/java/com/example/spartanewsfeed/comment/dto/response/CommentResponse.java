package com.example.spartanewsfeed.comment.dto.response;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final long id;
    private final long userId;
    private final long postId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponse(Long id, long userId, long postId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt ) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
