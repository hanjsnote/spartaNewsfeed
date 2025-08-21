package com.example.spartanewsfeed.user.service;

import com.example.spartanewsfeed.common.config.PasswordEncoder;
import com.example.spartanewsfeed.user.dto.request.UserDeleteRequest;
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

        String encodedPassword = passwordEncoder.encode(request.getPassword());     //요청받은 비밀번호를 암호화

        if (userRepository.existsByEmail(request.getEmail())) {     //이메일 중복 확인
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        //User 엔티티 생성 후 저장
        User user = new User(request.getEmail(), request.getName(), encodedPassword);
        userRepository.save(user);

        return new UserSignUpResponse( //응답 객체 생성
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.isPublic(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //회원 조회
    @Transactional(readOnly = true)
    public List<UserFindResponse> findUsers(String name) {

        List<User> users = userRepository.findAll(); //모든 회원 조회
        List<UserFindResponse> findUser = new ArrayList<>();

        if (name == null) {     //이름이 null이면 전체 조회
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
            if (name.equals(user.getName())) {  //이름이 일치하는 회원만 조회
                findUser.add(new UserFindResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.isPublic(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ));
            }
        }
        return findUser;
    }

    //회원 정보 수정
    public UserUpdateResponse updateUser(Long sessionUserId, Long id, UserUpdateRequest request) {

        User user = userRepository.findByIdOrElseThrow(id);    //id로 회원 조회 없으면 404 에러

        if (!id.equals(sessionUserId)) {    //본인의 계정이 아니면 403 에러
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 계정만 수정할 수 있습니다.");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {   //새 비밀번호와 기존 비밀번호가 다르면 403 에러
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        if (!passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {   //비밀번호가 같으면 400 에러
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호와 동일한 비밀번호로 수정할 수 없습니다.");
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());

        user.updateUser( //User 엔티티에 정보 수정 여기서 수정한 데이터를 userRepository.save() 로 저장하지 않는 이유는 JPA 영속성 컨텍스트의 감지 기능으로 인해 자동 반영
                request.getEmail(),
                request.getName(),
                encodedNewPassword,
                request.isPublic()
        );

        return new UserUpdateResponse(
                user.getEmail(),
                user.getName(),
                user.isPublic()
        );
    }

    //회원 삭제
    public void deleteUser(Long sessionUserId, Long id, UserDeleteRequest request) {

        User user = userRepository.findByIdOrElseThrow(id);  //id 존재 여부 확인

        if (!id.equals(sessionUserId)) {    //본인의 계정이 아니면 403 에러
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 계정만 삭제할 수 있습니다.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {   //비밀번호 검증
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(id);

    }

    //로그인
    public Long login(UserLoginRequest requestDto) {
        User user = userRepository.findByEmailOrElseThrow(requestDto.getEmail());   //이메일 존재 여부 확인

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {   //비밀번호 검증
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return user.getId();
    }
}

