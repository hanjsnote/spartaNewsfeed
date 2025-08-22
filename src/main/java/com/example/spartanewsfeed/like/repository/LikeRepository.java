package com.example.spartanewsfeed.like.repository;

import com.example.spartanewsfeed.like.entity.Like;
import com.example.spartanewsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPost(Post post);
    List<Like> findAllByPostId(Long postId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
}
