package com.example.spartanewsfeed.post.controller;

import com.example.spartanewsfeed.common.consts.Const;
import com.example.spartanewsfeed.post.dto.request.PatchRequest;
import com.example.spartanewsfeed.post.dto.request.PostRequest;
import com.example.spartanewsfeed.post.dto.response.*;
import com.example.spartanewsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId, // 상수로 세션키를 받음
//            @PathVariable Long userId,
            @Valid @RequestBody PostRequest postRequest
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

    // 팔로우한 계정들의 게시글만 전체 조회
    @GetMapping("/followers")
    public ResponseEntity<Page<FollowerGetResponse>> getFollowers(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId, // 상수로 세션키를 받음
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(postService.findFollowings(userId,page,size));
    }

    // 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<SingleGetResponse> getPostById(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page, // 댓글의 페이지 네이션
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postService.findPostById(postId, page, size));
    }

    // 게시물 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PatchResponse> updatePost(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId, // 상수로 세션키를 받음
//            @PathVariable Long userId, 유저 아이디를 이제 세션으로 정보를 받아오니 제외
            @PathVariable Long postId,
            @Valid @RequestBody PatchRequest patchRequest
    ) {
        return ResponseEntity.ok(postService.updatePost(userId, postId, patchRequest));
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId, // 상수로 세션키를 받음
//            @PathVariable Long userId, 유저 아이디를 이제 세션으로 정보를 받아오니 제외
            @PathVariable Long postId
    ) {
        postService.deletePost(userId, postId);
        return ResponseEntity.ok().build();
    }
}

