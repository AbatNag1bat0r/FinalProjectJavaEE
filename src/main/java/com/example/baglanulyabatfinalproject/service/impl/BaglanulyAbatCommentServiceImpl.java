package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatCommentDto.BaglanulyAbatCommentRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatCommentDto.BaglanulyAbatCommentResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatComment;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatCommentRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatTaskRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatUserRepository;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BaglanulyAbatCommentServiceImpl implements BaglanulyAbatCommentService {

    private final BaglanulyAbatCommentRepository commentRepository;
    private final BaglanulyAbatTaskRepository taskRepository;
    private final BaglanulyAbatUserRepository userRepository;

    @Override
    @Transactional
    public BaglanulyAbatCommentResponse createComment(BaglanulyAbatCommentRequest request) {
        log.info("Creating comment for task {}", request.getTaskId());

        BaglanulyAbatTask task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("Task", request.getTaskId()));
        BaglanulyAbatUser author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("User", request.getAuthorId()));

        BaglanulyAbatComment comment = BaglanulyAbatComment.builder()
                .content(request.getContent())
                .task(task)
                .author(author)
                .build();

        return toResponse(commentRepository.save(comment));
    }

    @Override
    public BaglanulyAbatCommentResponse getCommentById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public List<BaglanulyAbatCommentResponse> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatCommentResponse> getCommentsByAuthor(Long authorId) {
        return commentRepository.findByAuthorId(authorId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public BaglanulyAbatCommentResponse updateComment(Long id, String newContent) {
        log.info("Updating comment {}", id);
        BaglanulyAbatComment comment = findById(id);
        comment.setContent(newContent);
        return toResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        log.info("Deleting comment {}", id);
        commentRepository.delete(findById(id));
    }

    private BaglanulyAbatComment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("Comment", id));
    }

    private BaglanulyAbatCommentResponse toResponse(BaglanulyAbatComment c) {
        BaglanulyAbatUser u = c.getAuthor();
        BaglanulyAbatUserResponse authorResponse = BaglanulyAbatUserResponse.builder()
                .id(u.getId()).username(u.getUsername()).email(u.getEmail())
                .firstName(u.getFirstName()).lastName(u.getLastName())
                .role(u.getRole()).isActive(u.getIsActive()).build();

        return BaglanulyAbatCommentResponse.builder()
                .id(c.getId()).content(c.getContent()).isEdited(c.getIsEdited())
                .createdAt(c.getCreatedAt()).updatedAt(c.getUpdatedAt())
                .taskId(c.getTask().getId()).author(authorResponse).build();
    }
}