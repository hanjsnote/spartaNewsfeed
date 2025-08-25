package com.example.spartanewsfeed.user.controller;

import com.example.spartanewsfeed.common.consts.Const;
import com.example.spartanewsfeed.user.dto.request.UserDeleteRequest;
import com.example.spartanewsfeed.user.dto.request.UserSignUpRequest;
import com.example.spartanewsfeed.user.dto.request.UserUpdateRequest;
import com.example.spartanewsfeed.user.dto.response.UserFindResponse;
import com.example.spartanewsfeed.user.dto.response.UserSignUpResponse;
import com.example.spartanewsfeed.user.dto.response.UserUpdateResponse;
import com.example.spartanewsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<UserSignUpResponse> signUp(
            @Valid @RequestBody UserSignUpRequest request
    ) {
        UserSignUpResponse userSignUpResponse = userService.signUp(request);

        return new ResponseEntity<>(userSignUpResponse, HttpStatus.CREATED);
    }

    //회원 조회
    @GetMapping
    public ResponseEntity<List<UserFindResponse>> findUsers(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(userService.findUsers(userId, name));
    }

    //회원 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId,
            @PathVariable long id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, id, request));
    }

    //회원 탈퇴
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = Const.SESSION_KEY) Long userId,
            @PathVariable long id,
            @RequestBody UserDeleteRequest deleteRequest, HttpServletRequest servletRequest
    ) {
        userService.deleteUser(userId, id, deleteRequest);

        HttpSession session = servletRequest.getSession(false);
        if(session != null){
            session.invalidate();   //탈퇴와 동시에 해당 세션(데이터)를 삭제
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
