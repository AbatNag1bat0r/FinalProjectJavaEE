package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask.BaglanulyAbatTaskStatus;

import java.util.List;

public interface BaglanulyAbatTaskService {

    BaglanulyAbatTaskResponse createTask(BaglanulyAbatTaskRequest request);

    BaglanulyAbatTaskResponse getTaskById(Long id);

    List<BaglanulyAbatTaskResponse> getAllTasks();

    List<BaglanulyAbatTaskResponse> getTasksByAssignee(Long assigneeId);

    List<BaglanulyAbatTaskResponse> getTasksByProject(Long projectId);

    List<BaglanulyAbatTaskResponse> getTasksByStatus(BaglanulyAbatTaskStatus status);

    List<BaglanulyAbatTaskResponse> getTasksByPriority(BaglanulyAbatTaskPriority priority);

    BaglanulyAbatTaskResponse updateTask(Long id, BaglanulyAbatTaskRequest request);

    BaglanulyAbatTaskResponse updateTaskStatus(Long id, BaglanulyAbatTaskStatus status);

    void deleteTask(Long id);

    List<BaglanulyAbatTaskResponse> searchTasks(String query);
}