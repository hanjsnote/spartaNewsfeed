package com.example.spartanewsfeed.comment.dto.response;
import lombok.Getter ;
import java.time.LocalDateTime;

@Getter
public class PostCommentResponse {
    private final long id;
    private final long userId;
    private final String content;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostCommentResponse(Long id, Long userId, String name, String content,  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
