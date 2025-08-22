package com.example.spartanewsfeed.comment.controller;

import com.example.spartanewsfeed.comment.dto.request.CommentRequest;
import com.example.spartanewsfeed.comment.dto.response.CommentResponse;
import com.example.spartanewsfeed.comment.service.CommentService;
import com.example.spartanewsfeed.common.consts.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId,
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(userId, postId, commentRequest)); // 201 Create
    }

    @PatchMapping("/{commentId}") // 댓글 수정
    public ResponseEntity<CommentResponse> updateComment(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId,
            @PathVariable Long postId, // 쓸 곳이 없다.
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(userId, commentId, commentRequest)); // 200 OK
    }

    @GetMapping // 댓글 조회 page 적용
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page, // page 파라미터를 받아 현재 페이지 번호를 지정
            @RequestParam(defaultValue = "10") int size) { // 한 페이지에 보여줄 댓글의 수
        return ResponseEntity.ok(commentService.commentAll(postId, page, size));
    }

    @GetMapping("/{commentId}")  // 댓글 단건 조회
    public ResponseEntity<CommentResponse> getCommentById(
            @PathVariable Long postId, // 쓸 곳이 없다.
            @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findCommentById(commentId)); // 200 OK
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name =  Const.SESSION_KEY) Long userId,
            @PathVariable Long postId, // 쓸 곳이 없다.
            @PathVariable Long commentId) {
        commentService.deleteCommentById(userId, commentId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
