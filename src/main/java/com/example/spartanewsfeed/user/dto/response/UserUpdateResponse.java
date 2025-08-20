package com.example.spartanewsfeed.user.dto.response;

import lombok.Getter;

@Getter
public class UserUpdateResponse {

    private final String email;

    private final String name;

    private final boolean isPublic;

    public UserUpdateResponse(String email, String name, boolean isPublic){
        this.email = email;
        this.name = name;
        this.isPublic = isPublic;
    }



}
