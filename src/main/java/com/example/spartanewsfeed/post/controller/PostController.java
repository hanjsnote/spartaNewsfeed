package com.example.spartanewsfeed.post.controller;

import com.example.spartanewsfeed.post.dto.request.PatchRequest;
import com.example.spartanewsfeed.post.dto.request.PostRequest;
import com.example.spartanewsfeed.post.dto.response.GetResponse;
import com.example.spartanewsfeed.post.dto.response.PatchResponse;
import com.example.spartanewsfeed.post.dto.response.PostResponse;
import com.example.spartanewsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/posts")
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @SessionAttribute("sessionKey") Long userId,
//            @PathVariable Long userId,
            @RequestBody PostRequest postRequest
    ) {
        return ResponseEntity.ok(postService.createPost(userId, postRequest));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<Page<GetResponse>> getPosts(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postService.postAll(userId, page, size));
    }

//    // 단건 조회
//    @GetMapping("/{userId}/posts/{postId}")
//    public ResponseEntity<GetResponse> getPostById(
//            @PathVariable Long userId,
//            @PathVariable Long postId
//    ) {
//        return ResponseEntity.ok(postService.findPostById(userId, postId));
//    }

    // 단건 조회 유저 아이디 없는 버전
    @GetMapping("/{postId}")
    public ResponseEntity<GetResponse> getPostById(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    // 게시물 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PatchResponse> updatePost(
            @SessionAttribute("sessionKey") Long userId,
//            @PathVariable Long userId, 유저 아이디를 이제 세션으로 정보를 받아오니 제외
            @PathVariable Long postId,
            @RequestBody PatchRequest patchRequest
    ) {
        return ResponseEntity.ok(postService.updatePost(userId, postId, patchRequest));
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @SessionAttribute("sessionKey") Long userId,
//            @PathVariable Long userId, 유저 아이디를 이제 세션으로 정보를 받아오니 제외
            @PathVariable Long postId
    ) {
        postService.deletePost(userId, postId);
        return ResponseEntity.ok().build();
    }
}

