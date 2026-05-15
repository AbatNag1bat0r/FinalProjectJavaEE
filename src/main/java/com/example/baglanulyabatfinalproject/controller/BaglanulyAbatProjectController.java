package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatProjectDto.BaglanulyAbatProjectRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatProjectDto.BaglanulyAbatProjectResponse;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatProjectStatus;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class BaglanulyAbatProjectController {

    private final BaglanulyAbatProjectService projectService;

    @PostMapping
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatProjectResponse>> createProject(
            @Valid @RequestBody BaglanulyAbatProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaglanulyAbatApiResponse.success("Project created",
                        projectService.createProject(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatProjectResponse>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(projectService.getProjectById(id)));
    }

    @GetMapping
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatProjectResponse>>> getAll(
            @RequestParam(required = false) BaglanulyAbatProjectStatus status,
            @RequestParam(required = false) Long memberId) {

        List<BaglanulyAbatProjectResponse> projects;
        if (status != null) {
            projects = projectService.getProjectsByStatus(status);
        } else if (memberId != null) {
            projects = projectService.getProjectsByMember(memberId);
        } else {
            projects = projectService.getAllProjects();
        }
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(projects));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatProjectResponse>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody BaglanulyAbatProjectRequest request) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Project updated",
                projectService.updateProject(id, request)));
    }

    @PostMapping("/{projectId}/members/{userId}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatProjectResponse>> addMember(
            @PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Member added",
                projectService.addMember(projectId, userId)));
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatProjectResponse>> removeMember(
            @PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Member removed",
                projectService.removeMember(projectId, userId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Project deleted", null));
    }
}