package com.example.spartanewsfeed.user.entity;

import com.example.spartanewsfeed.common.entity.BaseEntity;
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

    public User(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void updateUser(String email, String name, String password){

        if (email != null) {
            this.email = email;
        }

        if (name != null) {
            this.name = name;
        }

        if (password != null) {
            this.password = password;
        }


    }
}
