package com.example.spartanewsfeed.post.service;

import com.example.spartanewsfeed.post.dto.request.PatchRequest;
import com.example.spartanewsfeed.post.dto.request.PostRequest;
import com.example.spartanewsfeed.post.dto.response.GetResponse;
import com.example.spartanewsfeed.post.dto.response.PatchResponse;
import com.example.spartanewsfeed.post.dto.response.PostResponse;
import com.example.spartanewsfeed.post.entity.Post;
import com.example.spartanewsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // 전체 트랜잭션 적용
public class PostService {
    private final PostRepository postRepository;

    // 게시물 작성
    public PostResponse createPost(PostRequest postRequest) {
        Post post = new Post( // 포스트 객체 생성
                postRequest.getTitle(),
                postRequest.getContent()
        );

        postRepository.save(post); // 레포지토리에 저장

        return new PostResponse( // 반환할 리스폰스 생성
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetResponse> postAll() {
        List<Post> posts = postRepository.findAll();

        List<GetResponse> GetResponses = new ArrayList<>(); // 반환할 리스트 생성

        for (Post post : posts) {                // 포스트에 담긴 값을 반환 리스트에 전달
            GetResponses.add(new GetResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreatedAt(),
                    post.getModifiedAt()
            ));
        }
        return GetResponses;
    }

    @Transactional(readOnly = true)
    public GetResponse findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );
        return new GetResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

    public PatchResponse updatePost(Long id, PatchRequest patchRequest) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );
        post.updatePost(
                patchRequest.getTitle(),
                patchRequest.getContent()
        );
        return new PatchResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

    public void deletePost(Long id) {
        postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물은 존재하지 않습니다.")
        );
        postRepository.deleteById(id);
    }
}
