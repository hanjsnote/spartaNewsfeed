package com.example.spartanewsfeed.post.repository;

import com.example.spartanewsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
