package com.example.spartanewsfeed.user.repository;

import com.example.spartanewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //email로 DB에 존재하는지 확인
    boolean existsByEmail(String email);

    //email로 사용자 조회
    Optional<User> findByEmail(String email);
}
