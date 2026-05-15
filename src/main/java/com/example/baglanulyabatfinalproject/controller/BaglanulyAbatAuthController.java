package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.*;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class BaglanulyAbatAuthController {

    private final BaglanulyAbatAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatAuthResponse>> register(
            @Valid @RequestBody BaglanulyAbatRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaglanulyAbatApiResponse.success("Registered successfully",
                        authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatAuthResponse>> login(
            @Valid @RequestBody BaglanulyAbatLoginRequest request) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Login successful",
                authService.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatAuthResponse>> refresh(
            @Valid @RequestBody BaglanulyAbatRefreshTokenRequest request) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Token refreshed",
                authService.refreshToken(request)));
    }
}