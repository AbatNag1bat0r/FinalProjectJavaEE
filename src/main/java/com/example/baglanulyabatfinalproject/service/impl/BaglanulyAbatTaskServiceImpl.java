package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatProject;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTag;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskStatus;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatAttachmentRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatCommentRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatProjectRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatTagRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatTaskRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatUserRepository;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BaglanulyAbatTaskServiceImpl implements BaglanulyAbatTaskService {

    private final BaglanulyAbatTaskRepository taskRepository;
    private final BaglanulyAbatUserRepository userRepository;
    private final BaglanulyAbatProjectRepository projectRepository;
    private final BaglanulyAbatTagRepository tagRepository;
    private final BaglanulyAbatCommentRepository commentRepository;
    private final BaglanulyAbatAttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public BaglanulyAbatTaskResponse createTask(BaglanulyAbatTaskRequest request) {
        log.info("Creating task: {}", request.getTitle());

        BaglanulyAbatTask.BaglanulyAbatTaskBuilder builder = BaglanulyAbatTask.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : BaglanulyAbatTaskStatus.TODO)
                .priority(request.getPriority() != null ? request.getPriority() : BaglanulyAbatTaskPriority.MEDIUM)
                .dueDate(request.getDueDate());

        if (request.getAssigneeId() != null) {
            BaglanulyAbatUser assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException(
                            "User", request.getAssigneeId()));
            builder.assignee(assignee);
        }

        if (request.getProjectId() != null) {
            BaglanulyAbatProject project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException(
                            "Project", request.getProjectId()));
            builder.project(project);
        }

        BaglanulyAbatTask task = builder.build();

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<BaglanulyAbatTag> tags = tagRepository.findAllById(request.getTagIds());
            task.setTags(tags);
        }

        BaglanulyAbatTask saved = taskRepository.save(task);
        log.info("Task created with id: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    public BaglanulyAbatTaskResponse getTaskById(Long id) {
        return toResponse(findTaskById(id));
    }

    @Override
    public List<BaglanulyAbatTaskResponse> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatTaskResponse> getTasksByAssignee(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatTaskResponse> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatTaskResponse> getTasksByStatus(BaglanulyAbatTaskStatus status) {
        return taskRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatTaskResponse> getTasksByPriority(BaglanulyAbatTaskPriority priority) {
        return taskRepository.findByPriority(priority).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public BaglanulyAbatTaskResponse updateTask(Long id, BaglanulyAbatTaskRequest request) {
        log.info("Updating task with id: {}", id);
        BaglanulyAbatTask task = findTaskById(id);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());

        if (request.getAssigneeId() != null) {
            BaglanulyAbatUser assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException(
                            "User", request.getAssigneeId()));
            task.setAssignee(assignee);
        }

        if (request.getProjectId() != null) {
            BaglanulyAbatProject project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException(
                            "Project", request.getProjectId()));
            task.setProject(project);
        }

        if (request.getTagIds() != null) {
            List<BaglanulyAbatTag> tags = tagRepository.findAllById(request.getTagIds());
            task.setTags(tags);
        }

        return toResponse(taskRepository.save(task));
    }

    @Override
    @Transactional
    public BaglanulyAbatTaskResponse updateTaskStatus(Long id, BaglanulyAbatTaskStatus status) {
        log.info("Updating task {} status to {}", id, status);
        BaglanulyAbatTask task = findTaskById(id);
        task.setStatus(status);
        return toResponse(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);
        taskRepository.delete(findTaskById(id));
    }

    @Override
    public List<BaglanulyAbatTaskResponse> searchTasks(String query) {
        return taskRepository.searchTasks(query).stream().map(this::toResponse).toList();
    }

    private BaglanulyAbatTask findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("Task", id));
    }

    private BaglanulyAbatTaskResponse toResponse(BaglanulyAbatTask task) {
        BaglanulyAbatUserResponse assigneeResponse = null;
        if (task.getAssignee() != null) {
            BaglanulyAbatUser u = task.getAssignee();
            assigneeResponse = BaglanulyAbatUserResponse.builder()
                    .id(u.getId()).username(u.getUsername()).email(u.getEmail())
                    .firstName(u.getFirstName()).lastName(u.getLastName())
                    .role(u.getRole()).isActive(u.getIsActive()).build();
        }

        List<String> tagNames = task.getTags() != null
                ? task.getTags().stream().map(BaglanulyAbatTag::getName).toList()
                : Collections.emptyList();

        Long projectId = task.getProject() != null ? task.getProject().getId() : null;
        String projectName = task.getProject() != null ? task.getProject().getName() : null;

        Integer commentsCount = commentRepository.countByTaskId(task.getId()).intValue();
        Integer attachmentsCount = attachmentRepository.countByTaskId(task.getId()).intValue();

        return BaglanulyAbatTaskResponse.builder()
                .id(task.getId()).title(task.getTitle()).description(task.getDescription())
                .status(task.getStatus()).priority(task.getPriority())
                .dueDate(task.getDueDate()).createdAt(task.getCreatedAt()).updatedAt(task.getUpdatedAt())
                .assignee(assigneeResponse).projectId(projectId).projectName(projectName)
                .tagNames(tagNames).commentsCount(commentsCount).attachmentsCount(attachmentsCount)
                .build();
    }
}