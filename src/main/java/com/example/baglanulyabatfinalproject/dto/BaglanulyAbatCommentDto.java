package com.example.baglanulyabatfinalproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class BaglanulyAbatCommentDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatCommentRequest {

        @NotBlank(message = "Comment content is required")
        private String content;

        @NotNull(message = "Task ID is required")
        private Long taskId;

        @NotNull(message = "Author ID is required")
        private Long authorId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatCommentResponse {

        private Long id;
        private String content;
        private Boolean isEdited;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long taskId;
        private BaglanulyAbatUserDto.BaglanulyAbatUserResponse author;
    }
}