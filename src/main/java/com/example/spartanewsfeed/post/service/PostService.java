package com.example.spartanewsfeed.post.service;

import com.example.spartanewsfeed.post.dto.request.PatchRequest;
import com.example.spartanewsfeed.post.dto.request.PostRequest;
import com.example.spartanewsfeed.post.dto.response.GetResponse;
import com.example.spartanewsfeed.post.dto.response.PatchResponse;
import com.example.spartanewsfeed.post.dto.response.PostResponse;
import com.example.spartanewsfeed.post.entity.Post;
import com.example.spartanewsfeed.post.repository.PostRepository;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional // 전체 트랜잭션 적용
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시물 작성
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        User user = userRepository.findById(userId).orElseThrow( // 유저 아이디 검증 로직
                // 존재하지 않는 유저라면 예외 처리
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        Post post = new Post( // 포스트 객체 생성
                postRequest.getTitle(),
                postRequest.getContent(),
                user
        );

        postRepository.save(post); // 레포지토리에 저장

        return new PostResponse( // 반환할 리스폰스 생성
                post.getId(),
                post.getUser().getId(), // 유저 아이디
                post.getUser().getName(), // 유저명
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

//    게시글 전체 조회
//    @Transactional(readOnly = true)
//    public List<GetResponse> postAll(Long userId) {
//        List<Post> posts;
//
//        if (userId != null) { // 유저의 아이디 값을 입력 받았다면
//            userRepository.findById(userId).orElseThrow( // 유저 아이디 검증 로직
//                    // 존재하지 않는 유저라면 예외 처리
//                    () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
//            );
//            // 게시글 저장소에서 유저 아이디로 조회
//            posts = postRepository.findAllByUserId(userId);
//        } else {
//            posts = postRepository.findAll();
//        }
//
//        List<GetResponse> GetResponses = new ArrayList<>(); // 반환할 리스트 생성
//
//        for (Post post : posts) {// 포스트에 담긴 값을 반환 리스트에 전달
//            GetResponses.add(new GetResponse(
//                    post.getId(),
//                    post.getUser().getId(), // 유저 아이디
//                    post.getUser().getName(), // 유저명
//                    post.getTitle(),
//                    post.getContent(),
//                    post.getCreatedAt(),
//                    post.getModifiedAt(),
//                    post.getLikeCount()
//            ));
//        }
//        return GetResponses;
//    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<GetResponse> postAll(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Post> posts;
        if(userId != null) {
            if(!userRepository.existsById(userId)) {
                throw new IllegalArgumentException("해당 유저는 존재하지 않습니다.");
            }
            posts = postRepository.findAllByUserId(userId, pageable);
        } else {
            posts = postRepository.findAll(pageable);
        }

        Set<Long> userIds = posts.stream()
                .map(post -> post.getUser().getId())
                .collect(Collectors.toSet());

        List<Long> userIdList = new ArrayList<>(userIds);
        List<User> userList = userRepository.findAllById(userIdList);

        return posts.map(post -> {
            Long id = post.getUser().getId();
            User user = userList.stream()
                    .filter(users -> users.getId().equals(id))
                    .findFirst()
                    .orElseThrow(
                    () -> new IllegalArgumentException("유저가 없습니다.")
            );

            return new GetResponse(
                    post.getId(),
                    user.getId(),
                    user.getName(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreatedAt(),
                    post.getModifiedAt(),
                    post.getLikeCount()
            );
        });
    }


    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public GetResponse findPostById(Long userId, Long id) {
        userRepository.findById(userId).orElseThrow( // 유저 아이디 검증 로직
                // 존재하지 않는 유저라면 예외 처리
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );

        return new GetResponse(
                post.getId(),
                post.getUser().getId(), // 유저 아이디
                post.getUser().getName(), // 유저명
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getLikeCount()
        );
    }

    // 게시글 수정
    public PatchResponse updatePost(Long userId, Long id, PatchRequest patchRequest) {
        userRepository.findById(userId).orElseThrow( // 유저 아이디 검증 로직
                // 존재하지 않는 유저라면 예외 처리
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );
        if (!userId.equals(post.getUser().getId())) {
            throw new IllegalArgumentException(
                    "해당 게시글을 작성한 유저가 아니라 수정이 불가능합니다."
            );
        }
        post.updatePost(
                patchRequest.getTitle(),
                patchRequest.getContent()
        );
        return new PatchResponse(
                post.getId(),
                post.getUser().getId(), // 유저 아이디
                post.getUser().getName(), // 유저명
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

    public void deletePost(Long userId, Long id) {
        userRepository.findById(userId).orElseThrow( // 유저 아이디 검증 로직
                // 존재하지 않는 유저라면 예외 처리
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );
        if (!userId.equals(post.getUser().getId())) {
            throw new IllegalArgumentException("게시글을 작성한 유저가 아니기 때문에 삭제할 수 없습니다.");
        }
        postRepository.deleteByUserIdAndId(userId, id);
    }
}
