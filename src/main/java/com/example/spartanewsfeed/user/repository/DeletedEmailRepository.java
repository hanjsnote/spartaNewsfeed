package com.example.spartanewsfeed.user.repository;

import com.example.spartanewsfeed.user.entity.DeletedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedEmailRepository extends JpaRepository<DeletedEmail, Long> {

    //email이 DB에 존재하는지 확인
    boolean existsByEmail(String email);
}
