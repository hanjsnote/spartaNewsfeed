package com.example.spartanewsfeed.follow.controller;

import com.example.spartanewsfeed.follow.service.FollowService;
import com.example.spartanewsfeed.follow.dto.response.FollowResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingId}/follow")
    public ResponseEntity<FollowResponse> follow(@PathVariable Long followingId, HttpServletRequest request) {
        Long followerId = (Long) request.getSession(false).getAttribute("sessionKey");

        followService.followUser(followerId, followingId);
        return ResponseEntity.ok(new FollowResponse("팔로우했습니다."));
    }

    @DeleteMapping("/{followingId}/follow")
    public ResponseEntity<FollowResponse> unfollow(@PathVariable Long followingId, HttpServletRequest request) {
        Long followerId = (Long) request.getSession(false).getAttribute("sessionKey");

        followService.unfollowUser(followerId, followingId);
        return ResponseEntity.ok(new FollowResponse("언팔로우됐습니다."));
    }
}
