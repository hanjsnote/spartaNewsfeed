package com.example.spartanewsfeed.comment.service;

import com.example.spartanewsfeed.comment.dto.request.RequestDto;
import com.example.spartanewsfeed.comment.dto.response.ResponseDto;
import com.example.spartanewsfeed.comment.entity.Comment;
import com.example.spartanewsfeed.comment.repository.CommentRepository;
import com.example.spartanewsfeed.post.entity.Post;
import com.example.spartanewsfeed.post.repository.PostRepository;
import com.example.spartanewsfeed.user.entity.User;
import com.example.spartanewsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private ResponseDto convertToResponseDto(Comment comment) {
        return new ResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional // 댓글 작성
    public ResponseDto createComment(Long userId, Long postId, RequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 user id 없습니다. "));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 post id 없습니다."));
        Comment newComment = new Comment(user, post, requestDto.getContent());
        Comment savedComment = commentRepository.save(newComment);
        return convertToResponseDto(savedComment);
    }

    @Transactional // 댓글 수정
    public ResponseDto updateComment(Long userId, Long commentId, RequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 comment id 없습니다. "));
        if(!comment.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        } // session 유저 id와 comment와 연관관계를 맺은 유저 id를 비교
        comment.commentUpdate(requestDto.getContent());
        return convertToResponseDto(comment);
    }

    @Transactional // 댓글 조회
    public List<ResponseDto> commentAll(Long postId){ // 여기서의 id는 post의 id를 의미하며, post에 적힌 댓글을 모두 출력
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 post id 없습니다.")); // 이 코드가 필요한지 잘 모르겠다.
        List<Comment> commentList = commentRepository.findByPost_Id(postId);
        List<ResponseDto> responseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            responseDtoList.add(convertToResponseDto(comment));
        }
        return responseDtoList;
    }

    @Transactional // 댓글 단건 조회
    public ResponseDto findCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 comment id 없습니다. "));
        return convertToResponseDto(comment);
    }

    @Transactional // 댓글 삭제
    public void deleteCommentById(Long userId, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 comment id 없습니다. "));
        if(!comment.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        } // session 유저 id와 comment와 연관관계를 맺은 유저 id를 비교
        commentRepository.delete(comment);
    }
}
