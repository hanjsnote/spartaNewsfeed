package com.example.spartanewsfeed.user.entity;

import com.example.spartanewsfeed.common.entity.BaseEntity;
import com.example.spartanewsfeed.user.dto.response.UpdateUserResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private boolean isPublic;

    public User(String email, String name, String password, boolean isPublic){
        this.email = email;
        this.name = name;
        this.password = password;
        this.isPublic = isPublic;
    }

    public void updateUser(String email, String name, String password, boolean isPublic){
        this.email = email;
        this.name = name;
        this.password = password;
        this.isPublic = isPublic;
    }

}
