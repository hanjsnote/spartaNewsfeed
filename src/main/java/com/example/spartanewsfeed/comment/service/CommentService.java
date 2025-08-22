package com.example.spartanewsfeed.comment.service;

import com.example.spartanewsfeed.comment.dto.request.CommentRequest;
import com.example.spartanewsfeed.comment.dto.response.CommentResponse;
import com.example.spartanewsfeed.comment.entity.Comment;
import com.example.spartanewsfeed.comment.repository.CommentRepository;
import com.example.spartanewsfeed.common.exception.DataNotFoundException;
import com.example.spartanewsfeed.common.exception.NoPermissionException;
import com.example.spartanewsfeed.post.entity.Post;
import com.example.spartanewsfeed.post.repository.PostRepository;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private CommentResponse convertToResponseDto(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional // 댓글 작성
    public CommentResponse createComment(Long userId, Long postId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("해당 유저 ID가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("해당 게시글 ID가 존재하지 않습니다."));
        Comment newComment = new Comment(user, post, commentRequest.getContent());
        Comment savedComment = commentRepository.save(newComment);
        return convertToResponseDto(savedComment);
    }

    @Transactional // 댓글 수정
    public CommentResponse updateComment(Long userId, Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("해당 댓글 ID가 존재하지 않습니다."));
        if(!comment.getUser().getId().equals(userId)){
            throw new NoPermissionException("댓글을 수정할 권한이 없습니다.");
        } // session 유저 id와 comment와 연관관계를 맺은 유저 id를 비교
        comment.commentUpdate(commentRequest.getContent());
        return convertToResponseDto(comment);
    }

    @Transactional// 댓글 조회 page 적용
    public List<CommentResponse> commentAll(Long postId, int page, int size) {
        postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("해당 게시글 ID가 존재하지 않습니다.")); // 이 코드가 필요한지 잘 모르겠다.
        Pageable pageable = PageRequest.of(page, size); // page 객체를 생성
        List<Comment> commentList = commentRepository.findByPost_Id(postId, pageable);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseList.add(convertToResponseDto(comment));
        }
        return commentResponseList;
    }

    @Transactional // 댓글 단건 조회
    public CommentResponse findCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("해당 댓글 ID가 존재하지 않습니다."));
        return convertToResponseDto(comment);
    }

    @Transactional // 댓글 삭제
    public void deleteCommentById(Long userId, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("해당 댓글 ID가 존재하지 않습니다."));
        if(!comment.getUser().getId().equals(userId)){
            throw new NoPermissionException("댓글을 삭제할 권한이 없습니다.");
        } // session 유저 id와 comment와 연관관계를 맺은 유저 id를 비교
        commentRepository.delete(comment);
    }
}
