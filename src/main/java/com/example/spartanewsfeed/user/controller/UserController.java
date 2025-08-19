package com.example.spartanewsfeed.user.controller;

import com.example.spartanewsfeed.user.dto.request.SignUpRequestDto;
import com.example.spartanewsfeed.user.dto.response.SignUpResponseDto;
import com.example.spartanewsfeed.user.dto.response.UserResponseDto;
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
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){

        SignUpResponseDto SignUpResponseDto = userService.signUp(requestDto);

        return new ResponseEntity<>(SignUpResponseDto, HttpStatus.CREATED);
    }

    //회원 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findUsers(@RequestParam(required = false) String name){

        List<UserResponseDto> userResponseDto = userService.findUsers(name);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }


}
