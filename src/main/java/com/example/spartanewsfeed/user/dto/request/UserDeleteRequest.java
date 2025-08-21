package com.example.spartanewsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserDeleteRequest {

    private final String password;

    public UserDeleteRequest(String password){
        this.password = password;
    }

}

