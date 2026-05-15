package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;

import java.util.List;

public interface BaglanulyAbatUserService {

    BaglanulyAbatUserResponse createUser(BaglanulyAbatUserRequest request);

    BaglanulyAbatUserResponse getUserById(Long id);

    BaglanulyAbatUserResponse getUserByUsername(String username);

    List<BaglanulyAbatUserResponse> getAllUsers();

    List<BaglanulyAbatUserResponse> getAllActiveUsers();

    BaglanulyAbatUserResponse updateUser(Long id, BaglanulyAbatUserRequest request);

    void deleteUser(Long id);

    void deactivateUser(Long id);

    List<BaglanulyAbatUserResponse> searchUsers(String query);
}