package com.example.spartanewsfeed.follow.service;

import com.example.spartanewsfeed.follow.entity.Follow;
import com.example.spartanewsfeed.follow.repository.FollowRepository;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void followUser(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new IllegalArgumentException("팔로우하는 사용자를 찾을 수 없습니다.")
        );
        User following = userRepository.findById(followingId).orElseThrow(
                () -> new IllegalArgumentException("팔로우 대상 사용자를 찾을 수 없습니다.")
        );

        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(follower, following);
        if (existingFollow.isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 되어있는 대상입니다.");
        }
        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
    }

    public void unfollowUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new IllegalArgumentException("언팔로우하는 사용자를 찾을 수 없습니다.")
        );
        User following = userRepository.findById(followingId).orElseThrow(
                () -> new IllegalArgumentException("언팔로우 대상 사용자를 찾을 수 없습니다.")
        );
        Follow followToDelete = followRepository.findByFollowerAndFollowing(follower, following).orElseThrow(
                () -> new IllegalArgumentException("팔로우 되어있지 않은 대상입니다."));
        followRepository.delete(followToDelete);
    }
}

