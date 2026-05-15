package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatUserRole;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatDuplicateResourceException;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatUserRepository;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BaglanulyAbatUserServiceImpl implements BaglanulyAbatUserService {

    private final BaglanulyAbatUserRepository userRepository;

    @Override
    @Transactional
    public BaglanulyAbatUserResponse createUser(BaglanulyAbatUserRequest request) {
        log.info("Creating user with username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BaglanulyAbatDuplicateResourceException(
                    "Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BaglanulyAbatDuplicateResourceException(
                    "Email already registered: " + request.getEmail());
        }

        BaglanulyAbatUser user = BaglanulyAbatUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole() != null ? request.getRole() : BaglanulyAbatUserRole.USER)
                .build();

        BaglanulyAbatUser saved = userRepository.save(user);
        log.info("User created with id: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    public BaglanulyAbatUserResponse getUserById(Long id) {
        return toResponse(findUserById(id));
    }

    @Override
    public BaglanulyAbatUserResponse getUserByUsername(String username) {
        BaglanulyAbatUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException(
                        "User not found with username: " + username));
        return toResponse(user);
    }

    @Override
    public List<BaglanulyAbatUserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatUserResponse> getAllActiveUsers() {
        return userRepository.findAllByIsActiveTrue().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public BaglanulyAbatUserResponse updateUser(Long id, BaglanulyAbatUserRequest request) {
        log.info("Updating user with id: {}", id);
        BaglanulyAbatUser user = findUserById(id);

        if (!user.getUsername().equals(request.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {
            throw new BaglanulyAbatDuplicateResourceException(
                    "Username already taken: " + request.getUsername());
        }
        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new BaglanulyAbatDuplicateResourceException(
                    "Email already registered: " + request.getEmail());
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        userRepository.delete(findUserById(id));
    }

    @Override
    @Transactional
    public void deactivateUser(Long id) {
        log.info("Deactivating user with id: {}", id);
        BaglanulyAbatUser user = findUserById(id);
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public List<BaglanulyAbatUserResponse> searchUsers(String query) {
        return userRepository.searchUsers(query).stream().map(this::toResponse).toList();
    }

    private BaglanulyAbatUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("User", id));
    }

    private BaglanulyAbatUserResponse toResponse(BaglanulyAbatUser user) {
        return BaglanulyAbatUserResponse.builder()
                .id(user.getId()).username(user.getUsername()).email(user.getEmail())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .role(user.getRole()).isActive(user.getIsActive())
                .createdAt(user.getCreatedAt()).updatedAt(user.getUpdatedAt())
                .build();
    }
}