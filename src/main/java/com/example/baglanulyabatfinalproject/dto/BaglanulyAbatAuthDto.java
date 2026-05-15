package com.example.baglanulyabatfinalproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class BaglanulyAbatAuthDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatLoginRequest {

        @NotBlank(message = "Username is required")
        private String username;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatRegisterRequest {

        @NotBlank @Size(min = 3, max = 50)
        private String username;

        @NotBlank @Email @Size(max = 100)
        private String email;

        @NotBlank @Size(min = 6, max = 100)
        private String password;

        @Size(max = 50)
        private String firstName;

        @Size(max = 50)
        private String lastName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatAuthResponse {

        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private long expiresIn;
        private BaglanulyAbatUserDto.BaglanulyAbatUserResponse user;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatRefreshTokenRequest {

        @NotBlank(message = "Refresh token is required")
        private String refreshToken;
    }
}