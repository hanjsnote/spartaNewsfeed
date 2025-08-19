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

        List<FindUserResponse> findUserResponse = userService.findUsers(name);

        return new ResponseEntity<>(findUserResponse, HttpStatus.OK);
    }

    //회원 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable long id, @RequestBody UpdateUserRequest request){

        UpdateUserResponse updateUserResponse = userService.updateUser(id, request);

        return new ResponseEntity<>(updateUserResponse, HttpStatus.OK);
    }

}
