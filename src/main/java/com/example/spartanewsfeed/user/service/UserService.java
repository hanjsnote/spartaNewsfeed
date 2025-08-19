package com.example.spartanewsfeed.user.service;

import com.example.spartanewsfeed.user.dto.request.SignUpRequestDto;
import com.example.spartanewsfeed.user.dto.response.SignUpResponseDto;
import com.example.spartanewsfeed.user.dto.response.UserResponseDto;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원 가입
    public SignUpResponseDto signUp(SignUpRequestDto requestDto){

        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        User user = new User(requestDto.getEmail(), requestDto.getName(), requestDto.getPassword(), requestDto.isPublic());
        userRepository.save(user);

        return new SignUpResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isPublic(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //유저 조회
    @Transactional(readOnly = true)
    public List<UserResponseDto> findUsers(String name) {

        List<User> users = userRepository.findAll();
        List<UserResponseDto> findUser = new ArrayList<>();

        if(name == null){
            for(User user : users){
                findUser.add(new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.isPublic(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ));
            }
            return findUser;
        }

        for(User user : users){
            if(name.equals(user.getName())){
                findUser.add(new UserResponseDto(
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

}
