package com.example.spartanewsfeed.user.controller;

import com.example.spartanewsfeed.user.dto.request.UserLoginRequest;
import com.example.spartanewsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest requestDto, HttpServletRequest request){

        Long id = userService.login(requestDto);

        HttpSession session = request.getSession(); //신규 세션 생성, JESSIONID 쿠키 발급
        session.setAttribute("sessionKey", id);

        return ResponseEntity.ok("로그인 성공");
    }
}
