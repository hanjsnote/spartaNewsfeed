package com.example.spartanewsfeed.like.controller;

import com.example.spartanewsfeed.common.consts.Const;
import com.example.spartanewsfeed.like.dto.LikeResponse;
import com.example.spartanewsfeed.like.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> toggleLike(
            @PathVariable("postId") Long postId,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute(Const.SESSION_KEY);
        return ResponseEntity.ok(likeService.toggleLike(postId, userId));
    }

    @GetMapping
    public ResponseEntity<List<LikeResponse>> getLikes(
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(likeService.getLikes(postId));
    }
}
