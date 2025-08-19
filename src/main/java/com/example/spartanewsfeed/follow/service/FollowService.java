package com.example.spartanewsfeed.follow.service;

import com.example.spartanewsfeed.follow.entity.Follow;
import com.example.spartanewsfeed.follow.repository.FollowRepository;
import com.example.spartanewsfeed.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void followUser(User follower, User following) {
        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(follower,following);
        if (existingFollow.isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 되어있는 대상입니다.");
        }
        Follow follow = new Follow(follower,following);
        followRepository.save(follow);
    }

    public void unfollowUser(User follower, User following) {
        Follow followToDelete = followRepository.findByFollowerAndFollowing(follower, following).orElseThrow(
                () -> new IllegalArgumentException("팔로우 되어있지 않은 대상입니다."));
        followRepository.delete(followToDelete);
    }
}

