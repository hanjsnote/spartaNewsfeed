package com.example.spartanewsfeed.post.entity;

import com.example.spartanewsfeed.common.entity.BaseEntity;
import com.example.spartanewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 유저를 필요할 때만 가져오겠다
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
    좋아요 유/무와 좋아요 갯수 포함시켜야함
    단건 조회시 댓글도 포함시켜서 출력
     */
    // 아직 라이크는 미구현이기 때문에 잠시 보류
    private int likeCount;
    public void addLike() {
        likeCount++;
    }
    public void removeLike() {
        likeCount--;
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
