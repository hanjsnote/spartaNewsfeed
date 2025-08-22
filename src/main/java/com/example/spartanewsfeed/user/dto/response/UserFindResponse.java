package com.example.spartanewsfeed.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserFindResponse {

    private final Long id;

    private final String email;

    private final String name;

    private final LocalDateTime createAt;

    private final LocalDateTime modifiedAt;

    public UserFindResponse(Long id, String email, String name, LocalDateTime createAt, LocalDateTime modifiedAt){
        this.id = id;
        this.email = email;
        this.name = name;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public UserFindResponse(String name, LocalDateTime createAt){
        this.id = null;
        this.email = null;
        this.name = name;
        this.createAt = createAt;
        this.modifiedAt = null;
    }

}
