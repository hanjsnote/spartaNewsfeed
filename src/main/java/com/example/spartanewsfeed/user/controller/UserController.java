package com.example.spartanewsfeed.user.controller;

import com.example.spartanewsfeed.user.dto.request.SignUpRequestDto;
import com.example.spartanewsfeed.user.dto.response.SignUpResponseDto;
import com.example.spartanewsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService UserService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){

        SignUpResponseDto SignUpResponseDto = UserService.signUp(requestDto);

        return new ResponseEntity<>(SignUpResponseDto, HttpStatus.CREATED);
    }

    //회원 전체 조회
//    @GetMapping
//    public


}
