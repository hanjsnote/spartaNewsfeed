package com.example.spartanewsfeed.like.service;

import com.example.spartanewsfeed.common.exception.DataNotFoundException;
import com.example.spartanewsfeed.like.dto.LikeResponse;
import com.example.spartanewsfeed.like.entity.Like;
import com.example.spartanewsfeed.like.repository.LikeRepository;
import com.example.spartanewsfeed.post.entity.Post;
import com.example.spartanewsfeed.post.repository.PostRepository;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // Post의 ID를 기준으로 좋아요를 겁색합니다.
    @Transactional(readOnly = true)
    public List<LikeResponse> getLikes(Long postId) {
        List<Like> likes = likeRepository.findByPostId(postId);
        return likes.stream().map(
                like -> new LikeResponse(
                        like.getLikeId(),
                        like.getUser().getId(),
                        like.getUser().getName()
                )
        ).collect(Collectors.toList());
    }

    // 새로운 좋아요를 생성하는 구문입니다.
    // 필요 파라미터 - 현재 세션의 유저 ID(getSessionAttribute), 접속한 게시물의 ID(URI 에서 받음)
    @Transactional
    public void toggleLike(Long postId, Long sessionId) {
        boolean exists = likeRepository.existsByPostIdAndUserId(postId, sessionId);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new DataNotFoundException("게시물을 찾을 수 없습니다.")
        );
        User user = userRepository.findById(sessionId).orElseThrow(
                () -> new DataNotFoundException("로그인 정보를 확인해주세요.")
        );
        if (!exists) {
            likeRepository.save(new Like(user, post));
        } else {
            likeRepository.deleteByPostIdAndUserId(postId, sessionId);
        }
    }
}
