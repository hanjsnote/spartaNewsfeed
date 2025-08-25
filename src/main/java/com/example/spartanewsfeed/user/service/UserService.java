package com.example.spartanewsfeed.user.service;

import com.example.spartanewsfeed.common.config.PasswordEncoder;
import com.example.spartanewsfeed.common.exception.GlobalException;
import com.example.spartanewsfeed.user.dto.request.UserDeleteRequest;
import com.example.spartanewsfeed.user.dto.request.UserLoginRequest;
import com.example.spartanewsfeed.user.dto.request.UserSignUpRequest;
import com.example.spartanewsfeed.user.dto.request.UserUpdateRequest;
import com.example.spartanewsfeed.user.dto.response.UserSignUpResponse;
import com.example.spartanewsfeed.user.dto.response.UserFindResponse;
import com.example.spartanewsfeed.user.dto.response.UserUpdateResponse;
import com.example.spartanewsfeed.user.entity.DeletedEmail;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.DeletedEmailRepository;
import com.example.spartanewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeletedEmailRepository deletedEmailRepository;  //탈퇴한 사용자 이메일 Repository
    private final PasswordEncoder passwordEncoder;

    //회원 가입
    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());     //요청받은 비밀번호를 암호화

        if (userRepository.existsByEmail(request.getEmail())) {     //이메일 중복 확인
            throw new GlobalException(HttpStatus.CONFLICT, "해당 이메일은 이미 사용중입니다.");
        }

        if (deletedEmailRepository.existsByEmail(request.getEmail())) { //탈퇴한 이메일과 비교
            throw new GlobalException(HttpStatus.NOT_FOUND, "탈퇴한 이메일 입니다.");
        }

        //User 엔티티 생성 후 저장
        User user = new User(request.getEmail(), request.getName(), encodedPassword);
        userRepository.save(user);

        return new UserSignUpResponse( //응답 객체 생성
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //회원 조회
    public List<UserFindResponse> findUsers(Long userId, String name) {

        List<User> users = userRepository.findAll();
        List<UserFindResponse> findUser = new ArrayList<>();

        if (name == null) {     //이름이 null이면 전체 조회
            for (User user : users) {
                if (userId.equals(user.getId())) {   //본인은 모든 정보 포함
                    findUser.add(new UserFindResponse(
                            user.getId(),
                            user.getEmail(),
                            user.getName(),
                            user.getCreatedAt(),
                            user.getModifiedAt()
                    ));
                } else {
                    findUser.add(new UserFindResponse(  //다른 유저는 민감 정보 제외
                            user.getName(),
                            user.getCreatedAt()
                    ));
                }
            }
            return findUser;
        }

        for (User user : users) {
            if (name.equals(user.getName()) && userId.equals(user.getId())) {  //이름이 일치하는 회원만 조회
                findUser.add(new UserFindResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ));
            } else if (name.equals(user.getName())){
                findUser.add(new UserFindResponse(
                        user.getName(),
                        user.getCreatedAt()
                ));
            }
        }
        return findUser;
    }

    //회원 정보 수정
    @Transactional
    public UserUpdateResponse updateUser(Long userId, Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id).orElseThrow(    //id로 회원 조회 없으면 404 에러
                () -> new GlobalException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다.")
        );

        if (!id.equals(userId)) {    //본인의 계정이 아니면 403 에러
            throw new GlobalException(HttpStatus.FORBIDDEN, "본인 계정만 수정할 수 있습니다.");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {   //새 비밀번호와 기존 비밀번호가 다르면 403 에러
            throw new GlobalException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {   //비밀번호가 같으면 409 에러
            throw new GlobalException(HttpStatus.CONFLICT, "현재 비밀번호와 동일한 비밀번호로 수정할 수 없습니다.");
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());

        user.updateUser( //User 엔티티에 정보 수정 여기서 수정한 데이터를 userRepository.save() 로 저장하지 않는 이유는 JPA 영속성 컨텍스트의 감지 기능으로 인해 자동 반영
                request.getEmail(),
                request.getName(),
                encodedNewPassword
        );

        return new UserUpdateResponse(
                user.getEmail(),
                user.getName()
        );
    }

    //회원 삭제
    @Transactional
    public void deleteUser(Long userId, Long id, UserDeleteRequest deleteRequest) {

        User user = userRepository.findById(id).orElseThrow(    //본인의 계정이 아니면 404 에러
                () -> new GlobalException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다.")
                );  //id 존재 여부 확인

        if (!id.equals(userId)) {    //본인의 계정이 아니면 403 에러
            throw new GlobalException(HttpStatus.FORBIDDEN, "본인 계정만 삭제할 수 있습니다.");
        }

        if (!passwordEncoder.matches(deleteRequest.getPassword(), user.getPassword())) {   //삭제시 비밀번호 검증
            throw new GlobalException(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다.");
        }

        DeletedEmail deletedEmail = new DeletedEmail(user.getEmail());
        deletedEmailRepository.save(deletedEmail);    //탈퇴한 사용자 이메일 저장

        userRepository.deleteById(id);
    }

    //로그인
    @Transactional
    public Long login(UserLoginRequest requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new GlobalException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.")
        );

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {   //비밀번호 검증
            throw new GlobalException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user.getId();
    }
}

