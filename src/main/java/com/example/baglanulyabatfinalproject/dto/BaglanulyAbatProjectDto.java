package com.example.baglanulyabatfinalproject.dto;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatProject.BaglanulyAbatProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BaglanulyAbatProjectDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatProjectRequest {

        @NotBlank(message = "Project name is required")
        @Size(max = 100)
        private String name;

        @Size(max = 500)
        private String description;

        private BaglanulyAbatProjectStatus status;

        private LocalDate startDate;

        private LocalDate endDate;

        private List<Long> memberIds;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatProjectResponse {

        private Long id;
        private String name;
        private String description;
        private BaglanulyAbatProjectStatus status;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer membersCount;
        private Integer tasksCount;
        private List<BaglanulyAbatUserDto.BaglanulyAbatUserResponse> members;
    }
}