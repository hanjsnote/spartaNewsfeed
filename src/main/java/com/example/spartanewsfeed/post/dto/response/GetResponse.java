package com.example.spartanewsfeed.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetResponse {
    private final Long id;
    /*
     private final Long userId,
     private final String userName
     */
    private final String title;
    private final String content;
    private final LocalDateTime created;
    private final LocalDateTime modified;

    public GetResponse(Long id,
                       String title,
                       /*
                       Long userId,
                       String userName
                       */
                       String content,
                       LocalDateTime created,
                       LocalDateTime modified) {
        this.id = id;
        this.title = title;
        /*
        this.userId = userId;
        this.userName = userName;
        */
        this.content = content;
        this.created = created;
        this.modified = modified;
    }
}