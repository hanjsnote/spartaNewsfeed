package com.example.spartanewsfeed.like.entity;

import com.example.spartanewsfeed.common.entity.BaseEntity;
import com.example.spartanewsfeed.post.entity.Post;
import com.example.spartanewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "likes", uniqueConstraints =
@UniqueConstraint(columnNames = {"user_id", "post_id"})) // 유니크 제약조건 : 이 둘은 같은 조합으로 두 개 이상의 레코드가 불가능.
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}

