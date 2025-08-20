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
    private final LocalDateTime created;
    private final LocalDateTime modified;

    public GetResponse(Long id,
                       Long userId,
                       String name,
                       String title,
                       String content,
                       LocalDateTime created,
                       LocalDateTime modified) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
    }
}