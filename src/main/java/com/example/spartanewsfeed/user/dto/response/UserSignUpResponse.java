package com.example.spartanewsfeed.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignUpResponse {

    private final Long id;

    private final String email;

    private final String name;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public UserSignUpResponse(Long id, String email, String name, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
