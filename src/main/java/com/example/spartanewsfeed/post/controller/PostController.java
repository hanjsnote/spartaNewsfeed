package com.example.spartanewsfeed.post.controller;

import com.example.spartanewsfeed.post.dto.request.PatchRequest;
import com.example.spartanewsfeed.post.dto.request.PostRequest;
import com.example.spartanewsfeed.post.dto.response.GetResponse;
import com.example.spartanewsfeed.post.dto.response.PatchResponse;
import com.example.spartanewsfeed.post.dto.response.PostResponse;
import com.example.spartanewsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            //@PathVariable Long id, 유저 아이디 받아야함
            @RequestBody PostRequest postRequest
    ) {
        return ResponseEntity.ok(postService.createPost(postRequest));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<GetResponse>> getPosts(
            //@RequestParam (required = false) Long id 아이디를 받을 수도 있고 아닐 수도 있다.
    ) {
        return ResponseEntity.ok(postService.postAll());
    }

    // 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<GetResponse> getPostById(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    // 게시물 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PatchResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PatchRequest patchRequest
    ) {
        return ResponseEntity.ok(postService.updatePost(postId, patchRequest));
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}

