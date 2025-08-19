package com.example.spartanewsfeed.like.service;

import com.example.spartanewsfeed.like.dto.LikeResponse;
import com.example.spartanewsfeed.like.dto.LikeSaveResponse;
import com.example.spartanewsfeed.like.entity.Like;
import com.example.spartanewsfeed.like.repository.LikeRepository;
import com.example.spartanewsfeed.post.repository.PostRepository;
import com.example.spartanewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 새로운 좋아요를 생성하는 구문입니다.
    // 필요 파라미터 - 현재 세션의 유저 ID(getSessionAttribute), 접속한 게시물의 ID(URI 에서 받음)
    @Transactional
    public LikeSaveResponse doLike(Long sessionId, Long postId) {
        // Like의 필드는 기본 식별자(ID), 외래키 유저, 외래키 포스트를 가집니다.
        // 생성자 또한 같습니다.
        Like like = new Like(
                userRepository.findById(sessionId).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "로그인 정보를 다시 확인해주세요.")
                ),
                postRepository.findById(postId).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다.")
                )
        );
        // 영속성 컨텍스트로 관리함.
        Like savedLike = likeRepository.save(like);

        // 저장된 객체(영속성 객체)로 Response를 생성하여 return 합니다.
        return new LikeSaveResponse(
                // Service 가 아닌 곳에서 객체 노출이 없도록 내부 데이터로 생성합니다.
                savedLike.getLikeId(),
                savedLike.getUser().getId(),
                savedLike.getUser().getName(),
                savedLike.getPost().getId(),
                savedLike.getCreatedAt()
        );
    }

    // Post의 ID를 기준으로 좋아요를 겁색합니다.
    @Transactional(readOnly = true)
    public List<LikeResponse> getLike(Long postId) {
        List<Like> likes = likeRepository.findByPostId(postId);
        return likes.stream().map(
                like -> new LikeResponse(
                        like.getLikeId(),
                        like.getUser().getId(),
                        like.getUser().getName()
                )
        ).collect(Collectors.toList());
    }

    // Post의 ID와 로그인중인 세션의 User ID를 파라미터로 받아 좋아요를 삭제합니다.
    @Transactional
    public void cancelLike(Long postId, Long sessionId) {
        likeRepository.delete(
                likeRepository.findByUserIdAndPostId(sessionId, postId).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물 혹은 좋아요 이력이 존재하지 않습니다.")
                )
        );
    }
}
