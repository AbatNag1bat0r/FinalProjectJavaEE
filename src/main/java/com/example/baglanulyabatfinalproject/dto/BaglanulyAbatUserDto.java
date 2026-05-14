package com.example.baglanulyabatfinalproject.dto;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser.BaglanulyAbatUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

public class BaglanulyAbatUserDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatUserRequest{
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100)
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
        private String password;

        @Size(max = 50)
        private String firstName;

        @Size(max = 50)
        private String lastName;

        private BaglanulyAbatUserRole role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatUserResponse{
        private Long id;
        private String username;
        private String email;
        private String fisrtName;
        private String lastName;
        private BaglanulyAbatUserRole role;
        private Boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
