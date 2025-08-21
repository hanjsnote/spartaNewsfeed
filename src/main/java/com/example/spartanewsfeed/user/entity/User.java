package com.example.spartanewsfeed.user.entity;

import com.example.spartanewsfeed.common.entity.BaseEntity;
import com.example.spartanewsfeed.follow.entity.Follow;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> following = new HashSet<>();

    private boolean isPublic;

    public User(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
        this.isPublic = true;
    }

    public void updateUser(String email, String name, String password, boolean isPublic){
        this.email = email;
        this.name = name;
        this.password = password;
        this.isPublic = isPublic;
    }

    public void addFollower(Follow follow){
        this.followers.add(follow);
    }

    public void addFollowing(Follow follow){
        this.following.add(follow);
    }

}
