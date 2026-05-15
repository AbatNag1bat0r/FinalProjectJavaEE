package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatAuthResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatLoginRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatRefreshTokenRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatRegisterRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatUserRole;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatBadRequestException;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatDuplicateResourceException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatUserRepository;
import com.example.baglanulyabatfinalproject.security.BaglanulyAbatJwtService;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaglanulyAbatAuthServiceImpl implements BaglanulyAbatAuthService {

    private final BaglanulyAbatUserRepository userRepository;
    private final BaglanulyAbatJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public BaglanulyAbatAuthResponse register(BaglanulyAbatRegisterRequest request) {
        log.info("Registering user: {}", request.getUsername());

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
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(BaglanulyAbatUserRole.USER)
                .build();

        BaglanulyAbatUser saved = userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getUsername());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return buildAuthResponse(accessToken, refreshToken, saved);
    }

    @Override
    public BaglanulyAbatAuthResponse login(BaglanulyAbatLoginRequest request) {
        log.info("Login attempt for: {}", request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        BaglanulyAbatUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BaglanulyAbatBadRequestException("Invalid credentials"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return buildAuthResponse(accessToken, refreshToken, user);
    }

    @Override
    public BaglanulyAbatAuthResponse refreshToken(BaglanulyAbatRefreshTokenRequest request) {
        String username = jwtService.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(request.getRefreshToken(), userDetails)) {
            throw new BaglanulyAbatBadRequestException("Invalid or expired refresh token");
        }

        BaglanulyAbatUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaglanulyAbatBadRequestException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return buildAuthResponse(newAccessToken, newRefreshToken, user);
    }

    private BaglanulyAbatAuthResponse buildAuthResponse(String accessToken,
                                                        String refreshToken,
                                                        BaglanulyAbatUser user) {
        BaglanulyAbatUserResponse userResponse = BaglanulyAbatUserResponse.builder()
                .id(user.getId()).username(user.getUsername()).email(user.getEmail())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .role(user.getRole()).isActive(user.getIsActive())
                .createdAt(user.getCreatedAt()).build();

        return BaglanulyAbatAuthResponse.builder()
                .accessToken(accessToken).refreshToken(refreshToken)
                .tokenType("Bearer").expiresIn(jwtService.getExpirationMs())
                .user(userResponse).build();
    }
}