package com.example.spartanewsfeed.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponseDto {

    private final Long userId;

    private final String email;

    private final String name;

    private final boolean isPublic;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public SignUpResponseDto (Long userId, String email, String name, boolean isPublic, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
