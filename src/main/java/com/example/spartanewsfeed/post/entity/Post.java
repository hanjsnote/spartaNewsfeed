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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*- 조건 : 게시물 수정, 삭제는 작성자 본인만 처리할 수 있습니다.
    - 예외처리 : 작성자가 아닌 다른 사용자가 게시물 수정, 삭제를 시도하는 경우

    [ ]  **뉴스피드 조회 기능**
    - 기본 정렬은 생성일자 ****기준으로 내림차순 정렬합니다.
    10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.
     */

    // 유저 아이디 받아야 함.
    // 유저 네임

    /*
    좋아요 유/무와 좋아요 갯수 포함시켜야함

    단건 조회시 댓글도 포함시켜서 출력
     */

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
