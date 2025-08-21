package com.example.spartanewsfeed.post.service;

import com.example.spartanewsfeed.like.repository.LikeRepository;
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
    private final LikeRepository likeRepository;

    // 게시물 작성
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인을 먼저 해주세요!");
        }
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

    /*
   [ ]  **뉴스피드 조회 기능**
   - 기본 정렬은 생성일자 ****기준으로 내림차순 정렬합니다.
   10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.
   N+1문제 고려해야한다.
    */

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<GetResponse> postAll(Long userId, int page, int size) {
        // 수정일 기준 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());

        Page<Post> posts;
        if (userId != null) { // 아이디 값이 존재 한다면
            if (!userRepository.existsById(userId)) { // 입력 받은 아이디가 회원으로 등록된 아이디가 아니라면..
                throw new IllegalArgumentException("해당 유저는 존재하지 않습니다.");  // 예외 처리
            }
            posts = postRepository.findAllByUserId(userId, pageable); // 존재한다면 아이디 기준으로 전체 조회
        } else {
            posts = postRepository.findAll(pageable); // 아이디 값이 없다면 전체 조회
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

    /*
    로그인 하지 않았다면 조회 불가
     */
    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public GetResponse findPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );
        int likeCount = likeRepository.countByPost(post);

        return new GetResponse(
                post.getId(),
                post.getUser().getId(), // 유저 아이디
                post.getUser().getName(), // 유저명
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                likeCount
        );
    }
    /*
    본인이 아니라면 수정 불가
     */

    // 게시글 수정
    public PatchResponse updatePost(Long userId, Long id, PatchRequest patchRequest) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인을 먼저 해주세요!");
        }
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

    /*
    해당 유저가 아니라면 삭제 불가
     */
    public void deletePost(Long userId, Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );
        if (!userId.equals(post.getUser().getId())) {
            throw new IllegalArgumentException("게시글을 작성한 유저가 아니기 때문에 삭제할 수 없습니다.");
        }
        postRepository.deleteByUserIdAndId(userId, id);
    }
}
