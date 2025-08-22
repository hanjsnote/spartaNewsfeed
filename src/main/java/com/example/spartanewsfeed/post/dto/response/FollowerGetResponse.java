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
    private final String content;
    private final LocalDateTime created;
    private final LocalDateTime modified;
    private final int likeCount; // 좋아요 갯수

    public FollowerGetResponse(Long id,
                               Long userId,
                               Long followerId,
                               String name,
                               String title,
                               String content,
                               LocalDateTime created,
                               LocalDateTime modified,
                               int likeCount
    ) {
        this.id = id;
        this.userId = userId;
        this.followerId = followerId;
        this.name = name;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.likeCount = likeCount;
    }

}
