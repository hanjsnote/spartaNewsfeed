package com.example.spartanewsfeed.user.service;

import com.example.spartanewsfeed.user.dto.request.SignUpRequest;
import com.example.spartanewsfeed.user.dto.request.UpdateUserRequest;
import com.example.spartanewsfeed.user.dto.response.SignUpResponse;
import com.example.spartanewsfeed.user.dto.response.FindUserResponse;
import com.example.spartanewsfeed.user.dto.response.UpdateUserResponse;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원 가입
    public SignUpResponse signUp(SignUpRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        User user = new User(request.getEmail(), request.getName(), request.getPassword(), request.isPublic());
        userRepository.save(user);

        return new SignUpResponse(
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
    public List<FindUserResponse> findUsers(String name) {

        List<User> users = userRepository.findAll();
        List<FindUserResponse> findUser = new ArrayList<>();

        if(name == null){
            for(User user : users){
                findUser.add(new FindUserResponse(
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

        for(User user : users){
            if(name.equals(user.getName())){
                findUser.add(new FindUserResponse(
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
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest request) {

        User users = userRepository.findByIdOrElseThrow(id);

        if(!users.getPassword().equals(request.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        users.updateUser(
                request.getEmail(),
                request.getName(),
                request.getNewPassword(),
                request.isPublic()
        );

        return new UpdateUserResponse(
                users.getEmail(),
                users.getName(),
                users.isPublic()
        );
    }

    //회원 삭제
    public void deleteUser(Long id){

        userRepository.findByIdOrElseThrow(id);
        userRepository.deleteById(id);

    }


}
