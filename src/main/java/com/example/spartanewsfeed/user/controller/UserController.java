package com.example.spartanewsfeed.user.controller;

import com.example.spartanewsfeed.user.dto.request.SignUpRequest;
import com.example.spartanewsfeed.user.dto.request.UpdateUserRequest;
import com.example.spartanewsfeed.user.dto.response.SignUpResponse;
import com.example.spartanewsfeed.user.dto.response.FindUserResponse;
import com.example.spartanewsfeed.user.dto.response.UpdateUserResponse;
import com.example.spartanewsfeed.user.service.UserService;
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
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request){

        SignUpResponse SignUpResponse = userService.signUp(request);

        return new ResponseEntity<>(SignUpResponse, HttpStatus.CREATED);
    }

    //회원 조회
    @GetMapping
    public ResponseEntity<List<FindUserResponse>> findUsers(@RequestParam(required = false) String name){

        return ResponseEntity.ok(userService.findUsers(name));
    }

    //회원 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable long id, @RequestBody UpdateUserRequest request){

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    //회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
