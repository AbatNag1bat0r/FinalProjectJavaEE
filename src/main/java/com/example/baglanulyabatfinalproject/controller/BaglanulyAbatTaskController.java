package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskResponse;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskStatus;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class BaglanulyAbatTaskController {

    private final BaglanulyAbatTaskService taskService;

    @PostMapping
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatTaskResponse>> createTask(
            @Valid @RequestBody BaglanulyAbatTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaglanulyAbatApiResponse.success("Task created successfully",
                        taskService.createTask(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatTaskResponse>> getTaskById(
            @PathVariable Long id) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(taskService.getTaskById(id)));
    }

    @GetMapping
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatTaskResponse>>> getTasks(
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) BaglanulyAbatTaskStatus status,
            @RequestParam(required = false) BaglanulyAbatTaskPriority priority) {

        List<BaglanulyAbatTaskResponse> tasks;
        if (assigneeId != null) {
            tasks = taskService.getTasksByAssignee(assigneeId);
        } else if (projectId != null) {
            tasks = taskService.getTasksByProject(projectId);
        } else if (status != null) {
            tasks = taskService.getTasksByStatus(status);
        } else if (priority != null) {
            tasks = taskService.getTasksByPriority(priority);
        } else {
            tasks = taskService.getAllTasks();
        }
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(tasks));
    }

    @GetMapping("/search")
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatTaskResponse>>> searchTasks(
            @RequestParam("q") String query) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(taskService.searchTasks(query)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatTaskResponse>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody BaglanulyAbatTaskRequest request) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Task updated successfully",
                taskService.updateTask(id, request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatTaskResponse>> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam BaglanulyAbatTaskStatus status) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Task status updated",
                taskService.updateTaskStatus(id, status)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Task deleted successfully", null));
    }
}