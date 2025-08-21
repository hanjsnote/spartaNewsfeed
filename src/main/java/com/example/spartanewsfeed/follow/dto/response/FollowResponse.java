package com.example.spartanewsfeed.follow.dto.response;

import lombok.Getter;

@Getter
public class FollowResponse {
    private final String message;

    public FollowResponse(String message) {
        this.message = message;
    }
}
