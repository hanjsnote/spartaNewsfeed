package com.example.spartanewsfeed.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String name;

    private boolean is_public;

    public User(String email, String password, String name, boolean is_public){

        this.email = email;
        this.password = password;
        this.name = name;
        this.is_public = is_public;

    }
}
