package com.example.baglanulyabatfinalproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

public class BaglanulyAbatTagDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatTagRequest {

        @NotBlank(message = "Tag name is required")
        @Size(max = 50)
        private String name;

        @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Color must be a valid hex code e.g. #FF5733")
        private String color;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatTagResponse {

        private Long id;
        private String name;
        private String color;
        private Integer tasksCount;
    }
}