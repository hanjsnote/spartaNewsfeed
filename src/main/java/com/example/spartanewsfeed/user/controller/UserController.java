package com.example.spartanewsfeed.user.controller;


import com.example.spartanewsfeed.user.dto.request.UserSignUpRequest;
import com.example.spartanewsfeed.user.dto.request.UserUpdateRequest;
import com.example.spartanewsfeed.user.dto.response.UserSignUpResponse;
import com.example.spartanewsfeed.user.dto.response.UserFindResponse;
import com.example.spartanewsfeed.user.dto.response.UserUpdateResponse;
import com.example.spartanewsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(@Valid @RequestBody UserSignUpRequest request) {

        UserSignUpResponse UserSignUpResponse = userService.signUp(request);

        return new ResponseEntity<>(UserSignUpResponse, HttpStatus.CREATED);
    }

    //회원 조회
    @GetMapping
    public ResponseEntity<List<UserFindResponse>> findUsers(@RequestParam(required = false) String name) {

        return ResponseEntity.ok(userService.findUsers(name));
    }

    //회원 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @Valid @SessionAttribute("sessionKey") Long sessionUserId, @PathVariable long id, @RequestBody UserUpdateRequest request) {

        return ResponseEntity.ok(userService.updateUser(sessionUserId, id, request));
    }

    //회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@SessionAttribute("sessionKey") Long sessionUserId, @PathVariable long id) {

        userService.deleteUser(sessionUserId, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
