package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatProjectDto.BaglanulyAbatProjectRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatProjectDto.BaglanulyAbatProjectResponse;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatProjectStatus;

import java.util.List;

public interface BaglanulyAbatProjectService {

    BaglanulyAbatProjectResponse createProject(BaglanulyAbatProjectRequest request);

    BaglanulyAbatProjectResponse getProjectById(Long id);

    List<BaglanulyAbatProjectResponse> getAllProjects();

    List<BaglanulyAbatProjectResponse> getProjectsByStatus(BaglanulyAbatProjectStatus status);

    List<BaglanulyAbatProjectResponse> getProjectsByMember(Long userId);

    BaglanulyAbatProjectResponse updateProject(Long id, BaglanulyAbatProjectRequest request);

    BaglanulyAbatProjectResponse addMember(Long projectId, Long userId);

    BaglanulyAbatProjectResponse removeMember(Long projectId, Long userId);

    void deleteProject(Long id);
}