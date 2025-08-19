package com.example.spartanewsfeed.user.repository;

import com.example.spartanewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
