package com.example.spartanewsfeed.post.dto.response;

import com.example.spartanewsfeed.comment.dto.response.PostCommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SingleGetResponse {
    private final Long id;
    private final Long userId;
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int likeCount; // 좋아요 갯수
    private final List<PostCommentResponse> comments;


    public SingleGetResponse(Long id,
                             Long userId,
                             String name,
                             String title,
                             String content,
                             LocalDateTime createdAt,
                             LocalDateTime modifiedAt,
                             int likeCount,
                             List<PostCommentResponse> comments) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
        this.comments = comments;
    }
}