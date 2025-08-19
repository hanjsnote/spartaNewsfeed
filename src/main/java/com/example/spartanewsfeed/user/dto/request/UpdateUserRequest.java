package com.example.spartanewsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private final String email;

    private final String oldPassword;

    private final String newPassword;

    private final String name;

    private final boolean isPublic;

    public UpdateUserRequest(String email, String oldPassword, String newPassword, String name, boolean isPublic) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.isPublic = isPublic;
    }

}
