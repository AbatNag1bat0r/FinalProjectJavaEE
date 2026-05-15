package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatAuthResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatLoginRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatRefreshTokenRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAuthDto.BaglanulyAbatRegisterRequest;

public interface BaglanulyAbatAuthService {

    BaglanulyAbatAuthResponse register(BaglanulyAbatRegisterRequest request);

    BaglanulyAbatAuthResponse login(BaglanulyAbatLoginRequest request);

    BaglanulyAbatAuthResponse refreshToken(BaglanulyAbatRefreshTokenRequest request);
}