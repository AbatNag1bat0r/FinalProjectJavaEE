package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatCommentDto.BaglanulyAbatCommentRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatCommentDto.BaglanulyAbatCommentResponse;

import java.util.List;

public interface BaglanulyAbatCommentService {

    BaglanulyAbatCommentResponse createComment(BaglanulyAbatCommentRequest request);

    BaglanulyAbatCommentResponse getCommentById(Long id);

    List<BaglanulyAbatCommentResponse> getCommentsByTask(Long taskId);

    List<BaglanulyAbatCommentResponse> getCommentsByAuthor(Long authorId);

    BaglanulyAbatCommentResponse updateComment(Long id, String newContent);

    void deleteComment(Long id);
}