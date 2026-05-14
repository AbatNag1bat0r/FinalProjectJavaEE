package com.example.baglanulyabatfinalproject.dto;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask.BaglanulyAbatTaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BaglanulyAbatTaskDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatTaskStatus {
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        private String title;

        private String description;

        private BaglanulyAbatTaskStatus status;

        private BaglanulyAbatTaskPriority priority;

        private LocalDate dueDate ;

        private Long assihneeId;

        private Long projectId;

        private List<Long> tagIds;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatTaskResponse{
        private Long id;
        private String title;
        private String description;
        private BaglanulyAbatTaskStatus status;
        private BaglanulyAbatTaskPriority priority;
        private LocalDate dueDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private BaglanulyAbatUserDto.BaglanulyAbatUserResponse assignee;
        private Long projectId;
        private String projectName;
        private List<String> tagNames;
        private Integer commentsCount;
        private Integer attachmentsCount;
    }
}
