package com.example.spartanewsfeed.comment.repository;

import com.example.spartanewsfeed.comment.entity.Comment;
import com.example.spartanewsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost_Id(Long postId, Pageable pageable);
    Page<Comment> findByPost(Post post, Pageable pageable);
}
