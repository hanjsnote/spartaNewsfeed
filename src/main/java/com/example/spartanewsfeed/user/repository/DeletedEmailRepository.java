package com.example.spartanewsfeed.user.repository;

import com.example.spartanewsfeed.user.entity.DeletedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedEmailRepository extends JpaRepository<DeletedEmail, Long> {
    boolean existsByEmail(String email);
}
