package com.example.spartanewsfeed.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FollowerGetResponse {
    private final Long id;
    private final Long userId;
    private final Long followerId;
    private final String name;
    private final String title;
    private final LocalDateTime created;
    private final LocalDateTime modified;

    public FollowerGetResponse(Long id,
                               Long userId,
                               Long followerId,
                               String name,
                               String title,
                               LocalDateTime created,
                               LocalDateTime modified
    ) {
        this.id = id;
        this.userId = userId;
        this.followerId = followerId;
        this.name = name;
        this.title = title;
        this.created = created;
        this.modified = modified;
    }

}
