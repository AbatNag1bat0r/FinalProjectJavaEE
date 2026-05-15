package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatProjectDto.BaglanulyAbatProjectRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatProjectDto.BaglanulyAbatProjectResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatProject;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatProjectStatus;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatBadRequestException;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatDuplicateResourceException;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatProjectRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatTaskRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatUserRepository;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BaglanulyAbatProjectServiceImpl implements BaglanulyAbatProjectService {

    private final BaglanulyAbatProjectRepository projectRepository;
    private final BaglanulyAbatUserRepository userRepository;
    private final BaglanulyAbatTaskRepository taskRepository;

    @Override
    @Transactional
    public BaglanulyAbatProjectResponse createProject(BaglanulyAbatProjectRequest request) {
        log.info("Creating project: {}", request.getName());
        if (projectRepository.existsByName(request.getName())) {
            throw new BaglanulyAbatDuplicateResourceException(
                    "Project with name already exists: " + request.getName());
        }

        BaglanulyAbatProject project = BaglanulyAbatProject.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : BaglanulyAbatProjectStatus.ACTIVE)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<BaglanulyAbatUser> members = userRepository.findAllById(request.getMemberIds());
            project.setMembers(new ArrayList<>(members));
        }

        return toResponse(projectRepository.save(project));
    }

    @Override
    public BaglanulyAbatProjectResponse getProjectById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public List<BaglanulyAbatProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatProjectResponse> getProjectsByStatus(BaglanulyAbatProjectStatus status) {
        return projectRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatProjectResponse> getProjectsByMember(Long userId) {
        return projectRepository.findProjectsByMemberId(userId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public BaglanulyAbatProjectResponse updateProject(Long id, BaglanulyAbatProjectRequest request) {
        log.info("Updating project {}", id);
        BaglanulyAbatProject project = findById(id);

        if (!project.getName().equals(request.getName())
                && projectRepository.existsByName(request.getName())) {
            throw new BaglanulyAbatDuplicateResourceException(
                    "Project name already exists: " + request.getName());
        }

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        if (request.getStatus() != null) project.setStatus(request.getStatus());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());

        if (request.getMemberIds() != null) {
            List<BaglanulyAbatUser> members = userRepository.findAllById(request.getMemberIds());
            project.setMembers(new ArrayList<>(members));
        }

        return toResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public BaglanulyAbatProjectResponse addMember(Long projectId, Long userId) {
        BaglanulyAbatProject project = findById(projectId);
        BaglanulyAbatUser user = userRepository.findById(userId)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("User", userId));

        boolean alreadyMember = project.getMembers().stream()
                .anyMatch(m -> m.getId().equals(userId));
        if (alreadyMember) {
            throw new BaglanulyAbatBadRequestException("User is already a member of this project");
        }

        project.getMembers().add(user);
        return toResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public BaglanulyAbatProjectResponse removeMember(Long projectId, Long userId) {
        BaglanulyAbatProject project = findById(projectId);
        project.getMembers().removeIf(m -> m.getId().equals(userId));
        return toResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        projectRepository.delete(findById(id));
    }

    private BaglanulyAbatProject findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("Project", id));
    }

    private BaglanulyAbatUserResponse toUserResponse(BaglanulyAbatUser u) {
        return BaglanulyAbatUserResponse.builder()
                .id(u.getId()).username(u.getUsername()).email(u.getEmail())
                .firstName(u.getFirstName()).lastName(u.getLastName())
                .role(u.getRole()).isActive(u.getIsActive()).build();
    }

    private BaglanulyAbatProjectResponse toResponse(BaglanulyAbatProject p) {
        List<BaglanulyAbatUserResponse> members = p.getMembers().stream()
                .map(this::toUserResponse).toList();
        int tasksCount = taskRepository.findByProjectId(p.getId()).size();

        return BaglanulyAbatProjectResponse.builder()
                .id(p.getId()).name(p.getName()).description(p.getDescription())
                .status(p.getStatus()).startDate(p.getStartDate()).endDate(p.getEndDate())
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .membersCount(members.size()).tasksCount(tasksCount)
                .members(members).build();
    }
}