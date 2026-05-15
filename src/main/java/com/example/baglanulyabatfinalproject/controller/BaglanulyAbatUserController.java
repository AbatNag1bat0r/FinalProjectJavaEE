package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class BaglanulyAbatUserController {

    private final BaglanulyAbatUserService userService;

    @PostMapping
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatUserResponse>> createUser(
            @Valid @RequestBody BaglanulyAbatUserRequest request) {
        log.info("POST /api/v1/users — createUser: {}", request.getUsername());
        BaglanulyAbatUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaglanulyAbatApiResponse.success("User created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatUserResponse>> getUserById(
            @PathVariable Long id) {
        log.info("GET /api/v1/users/{}", id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(userService.getUserById(id)));
    }

    @GetMapping
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatUserResponse>>> getAllUsers(
            @RequestParam(required = false) Boolean activeOnly) {
        log.info("GET /api/v1/users — activeOnly={}", activeOnly);
        List<BaglanulyAbatUserResponse> users = Boolean.TRUE.equals(activeOnly)
                ? userService.getAllActiveUsers()
                : userService.getAllUsers();
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(users));
    }

    @GetMapping("/search")
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatUserResponse>>> searchUsers(
            @RequestParam("q") String query) {
        log.info("GET /api/v1/users/search?q={}", query);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(userService.searchUsers(query)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatUserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody BaglanulyAbatUserRequest request) {
        log.info("PUT /api/v1/users/{}", id);
        return ResponseEntity.ok(
                BaglanulyAbatApiResponse.success("User updated successfully",
                        userService.updateUser(id, request)));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        log.info("PATCH /api/v1/users/{}/deactivate", id);
        userService.deactivateUser(id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("User deactivated", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> deleteUser(@PathVariable Long id) {
        log.info("DELETE /api/v1/users/{}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("User deleted successfully", null));
    }
}