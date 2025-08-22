package com.example.spartanewsfeed.user.dto.response;

import lombok.Getter;

@Getter
public class UserUpdateResponse {

    private final String email;

    private final String name;

    public UserUpdateResponse(String email, String name){
        this.email = email;
        this.name = name;
    }



}
