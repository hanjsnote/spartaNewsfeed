package com.example.spartanewsfeed.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "deleted_emails")
public class DeletedEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    protected DeletedEmail() {}

    public DeletedEmail(String email) {
        this.email = email;
    }

}
