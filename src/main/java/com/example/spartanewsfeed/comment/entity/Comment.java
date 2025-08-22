package com.example.spartanewsfeed.comment.entity;

import com.example.spartanewsfeed.common.entity.BaseEntity;
import com.example.spartanewsfeed.post.entity.Post;
import com.example.spartanewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter ;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }
    public void commentUpdate(String content) {
        this.content = content ;
    }
}
