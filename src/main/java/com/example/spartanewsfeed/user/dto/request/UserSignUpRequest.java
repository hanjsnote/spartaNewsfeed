package com.example.spartanewsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserSignUpRequest {

    private final String email;

    private final String name;

    private final String password;

    private final boolean isPublic;

    public UserSignUpRequest(String email, String name, String password, boolean isPublic){
        this.email = email;
        this.name = name;
        this.password = password;
        this.isPublic = isPublic;
    }

}
