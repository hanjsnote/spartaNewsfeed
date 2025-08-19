package com.example.spartanewsfeed.like.dto;

import com.example.spartanewsfeed.like.entity.Like;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LikeResponse {
    private final long likeId;
    private final long userId;
    private final String userName;

    public LikeResponse(long likeId, long userId, String userName) {
        this.likeId = likeId;
        this.userId = userId;
        this.userName = userName;
    }
}
