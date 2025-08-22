package com.example.spartanewsfeed.post.repository;

import com.example.spartanewsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    void deleteByUserIdAndId(Long userId, Long id);

    Page<Post> findAllByUserIdIn(List<Long> userIds, Pageable pageable);
}
