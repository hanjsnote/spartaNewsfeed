package com.example.spartanewsfeed.user.service;

import com.example.spartanewsfeed.common.config.PasswordEncoder;
import com.example.spartanewsfeed.user.dto.request.UserLoginRequest;
import com.example.spartanewsfeed.user.dto.request.UserSignUpRequest;
import com.example.spartanewsfeed.user.dto.request.UserUpdateRequest;
import com.example.spartanewsfeed.user.dto.response.UserSignUpResponse;
import com.example.spartanewsfeed.user.dto.response.UserFindResponse;
import com.example.spartanewsfeed.user.dto.response.UserUpdateResponse;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 가입
    public UserSignUpResponse signUp(UserSignUpRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        User user = new User(request.getEmail(), request.getName(), encodedPassword, request.isPublic());
        userRepository.save(user);

        return new UserSignUpResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isPublic(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //회원 조회
    @Transactional(readOnly = true)
    public List<UserFindResponse> findUsers(String name) {

        List<User> users = userRepository.findAll();
        List<UserFindResponse> findUser = new ArrayList<>();

        if (name == null) {
            for (User user : users) {
                findUser.add(new UserFindResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.isPublic(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ));
            }
            return findUser;
        }

        for (User user : users) {
            if (name.equals(user.getName())) {
                findUser.add(new UserFindResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.isPublic(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ));
            }
        }
        return findUser;
    }

    //회원 수정
    public UserUpdateResponse updateUser(Long id, UserUpdateRequest request) {

        User users = userRepository.findByIdOrElseThrow(id);

        if (!users.getPassword().equals(request.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        users.updateUser(
                request.getEmail(),
                request.getName(),
                request.getNewPassword(),
                request.isPublic()
        );

        return new UserUpdateResponse(
                users.getEmail(),
                users.getName(),
                users.isPublic()
        );
    }

    //회원 삭제
    public void deleteUser(Long id) {

        userRepository.findByIdOrElseThrow(id);
        userRepository.deleteById(id);

    }

    //로그인
    @Transactional(readOnly = true)
    public Long login(UserLoginRequest requestDto) {
        User user = userRepository.findByEmailOrElseThrow(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        return user.getId();
    }
}

