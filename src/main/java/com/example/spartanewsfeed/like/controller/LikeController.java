package com.example.spartanewsfeed.like.controller;

import com.example.spartanewsfeed.like.dto.LikeResponse;
import com.example.spartanewsfeed.like.dto.LikeSaveResponse;
import com.example.spartanewsfeed.like.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    // TODO : ("유저 로그인 세션 키 속성의 키값 입력하기 세션 키 상수로 변경 시 입력 잊으면 고릴라가 되어서 동물원에서 살아라 바나나 먹으면서")

    @PostMapping("")
    public ResponseEntity<LikeSaveResponse> doLike(
            @PathVariable("postId") Long postId,
            HttpSession session
    ) {
        Long sessionId = (Long) session.getAttribute("PUT_SESSION_KEY");
        return new ResponseEntity<>(likeService.doLike(postId, sessionId), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<LikeResponse>> getLikes(
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(likeService.getLike(postId));
    }

    @DeleteMapping("")
    public ResponseEntity<Void> cancelLike(
            @PathVariable("postId") Long postId,
            HttpSession session
    ) {
        Long sessionId = (Long) session.getAttribute("PUT_SESSION_KEY");
        likeService.cancelLike(postId, sessionId);
        return ResponseEntity.noContent().build();
    }
}
