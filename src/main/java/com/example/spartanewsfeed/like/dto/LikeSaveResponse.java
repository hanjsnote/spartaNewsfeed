package com.example.spartanewsfeed.like.dto;

import com.example.spartanewsfeed.like.entity.Like;

import java.time.LocalDateTime;

public class LikeSaveResponse {
    private final long likeId;
    private final long userId;
    private final String userName;
    private final long postId;
    private final LocalDateTime createdAt;

    public LikeSaveResponse(long likeId, long userId, String userName, long postId, LocalDateTime createdAt) {
        this.likeId = likeId;
        this.userId = userId;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
    }
}
