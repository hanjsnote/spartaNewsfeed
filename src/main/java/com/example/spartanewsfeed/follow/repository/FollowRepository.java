package com.example.spartanewsfeed.follow.repository;

import com.example.spartanewsfeed.follow.entity.Follow;
import com.example.spartanewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
