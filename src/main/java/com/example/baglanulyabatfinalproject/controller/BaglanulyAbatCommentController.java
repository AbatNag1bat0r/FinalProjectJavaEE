package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatCommentDto.BaglanulyAbatCommentRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatCommentDto.BaglanulyAbatCommentResponse;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatCommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class BaglanulyAbatCommentController {

    private final BaglanulyAbatCommentService commentService;

    @PostMapping
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatCommentResponse>> create(
            @Valid @RequestBody BaglanulyAbatCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaglanulyAbatApiResponse.success("Comment created",
                        commentService.createComment(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatCommentResponse>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(commentService.getCommentById(id)));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatCommentResponse>>> getByTask(
            @PathVariable Long taskId) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(commentService.getCommentsByTask(taskId)));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatCommentResponse>>> getByAuthor(
            @PathVariable Long authorId) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(commentService.getCommentsByAuthor(authorId)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatCommentResponse>> update(
            @PathVariable Long id,
            @RequestParam @NotBlank String content) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Comment updated",
                commentService.updateComment(id, content)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> delete(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Comment deleted", null));
    }
}