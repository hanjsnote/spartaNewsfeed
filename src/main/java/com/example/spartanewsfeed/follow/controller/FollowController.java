package com.example.spartanewsfeed.follow.controller;

import com.example.spartanewsfeed.follow.service.FollowService;
import com.example.spartanewsfeed.follow.dto.request.FollowRequestDto;
import com.example.spartanewsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.spartanewsfeed.user.repository.UserRepository;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@PathVariable Long userId, @RequestBody FollowRequestDto requestDto) {
        try {
            User follower = userRepository.findById(userId).orElseThrow(
                    () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            User following = userRepository.findById(requestDto.getFollowingId()).orElseThrow(
                    () -> new IllegalArgumentException("팔로우 대상을 찾을 수 없습니다."));
            followService.followUser(follower, following);

            return ResponseEntity.ok("팔로우했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/follow")
    public ResponseEntity<String> unfollow(@PathVariable Long userId, @RequestBody FollowRequestDto requestDto) {
        try {
            User follower = userRepository.findById(userId).orElseThrow(
                    () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            User following = userRepository.findById(requestDto.getFollowingId()).orElseThrow(
                    () -> new IllegalArgumentException("언팔로우 대상을 찾을 수 없습니다."));
            followService.unfollowUser(follower, following);

            return ResponseEntity.ok("언팔로우됐습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
