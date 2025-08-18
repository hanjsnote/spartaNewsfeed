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

    private boolean isPublic;

    public User(String email, String password, String name, boolean isPublic){

        this.email = email;
        this.password = password;
        this.name = name;
        this.isPublic = isPublic;

    }
}
