package com.example.spartanewsfeed.follow.service;

import com.example.spartanewsfeed.follow.entity.Follow;
import com.example.spartanewsfeed.follow.repository.FollowRepository;
import com.example.spartanewsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void followUser(Long followerId, Long followingId) {
        if (!userRepository.existsById(followerId) || !userRepository.existsById(followingId)) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new IllegalArgumentException("이미 팔로우 되어있는 대상입니다.");
        }

        followRepository.save(new Follow(
                userRepository.findById(followerId).get(),
                userRepository.findById(followingId).get()
        ));
    }

    public void unfollowUser(Long followerId, Long followingId) {
        // User 존재 여부 확인
        if (!userRepository.existsById(followerId) || !userRepository.existsById(followingId)) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        // 삭제하기 전에 존재하는지 먼저 확인 (에러 메시지를 위함)
        if (!followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new IllegalArgumentException("팔로우 되어있지 않은 대상입니다.");
        }

        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }
}

