package com.example.spartanewsfeed.comment.controller;

import com.example.spartanewsfeed.comment.dto.request.RequestDto;
import com.example.spartanewsfeed.comment.dto.response.ResponseDto;
import com.example.spartanewsfeed.comment.service.CommentService;
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
    public ResponseEntity<ResponseDto> createComment(
            @SessionAttribute(name = "sessionKey") Long userId,
            @PathVariable Long postId,
            @RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(userId, postId, requestDto)); // 201 Create
        //return ResponseEntity.ok(commentService.createComment(userId, postId, requestDto)); // 200 OK 혹시 몰라 남겨두었습니다.
    }

    @PatchMapping("/{commentId}") // 댓글 수정
    public ResponseEntity<ResponseDto> updateComment(
            @SessionAttribute(name = "sessionKey") Long userId,
            @PathVariable Long postId, // 쓸 곳이 없다.
            @PathVariable Long commentId,
            @RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(commentService.updateComment(userId, commentId, requestDto)); // 200 OK
    }

    /*
    @GetMapping // 댓글 조회
    public ResponseEntity<List<ResponseDto>> getComments(
            @PathVariable Long postId){
        return ResponseEntity.ok(commentService.commentAll(postId)); // 200 OK
    }
     */

    @GetMapping // 댓글 조회 page 적용
    public ResponseEntity<List<ResponseDto>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page, // page 파라미터를 받아 현재 페이지 번호를 지정
            @RequestParam(defaultValue = "10") int size) { // 한 페이지에 보여줄 댓글의 수
        return ResponseEntity.ok(commentService.commentAll(postId, page, size));
    }

    @GetMapping("/{commentId}")  // 댓글 단건 조회
    public ResponseEntity<ResponseDto> getCommentById(
            @PathVariable Long postId, // 쓸 곳이 없다.
            @PathVariable Long commentId){
        return ResponseEntity.ok(commentService.findCommentById(commentId)); // 200 OK
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "sessionKey") Long userId,
            @PathVariable Long postId, // 쓸 곳이 없다.
            @PathVariable Long commentId) {
        commentService.deleteCommentById(userId, commentId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
