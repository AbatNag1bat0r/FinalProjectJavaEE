package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTaskDto.BaglanulyAbatTaskResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskStatus;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.*;
import com.example.baglanulyabatfinalproject.service.impl.BaglanulyAbatTaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BaglanulyAbatTaskService Unit Tests")
class BaglanulyAbatTaskServiceTest {

    @Mock private BaglanulyAbatTaskRepository taskRepository;
    @Mock private BaglanulyAbatUserRepository userRepository;
    @Mock private BaglanulyAbatProjectRepository projectRepository;
    @Mock private BaglanulyAbatTagRepository tagRepository;
    @Mock private BaglanulyAbatCommentRepository commentRepository;
    @Mock private BaglanulyAbatAttachmentRepository attachmentRepository;

    @InjectMocks
    private BaglanulyAbatTaskServiceImpl taskService;

    private BaglanulyAbatTask testTask;

    @BeforeEach
    void setUp() {
        testTask = BaglanulyAbatTask.builder()
                .id(1L).title("Test Task").description("Description")
                .status(BaglanulyAbatTaskStatus.TODO).priority(BaglanulyAbatTaskPriority.MEDIUM)
                .createdAt(LocalDateTime.now()).build();
    }

    @Test
    @DisplayName("createTask — minimal request success")
    void createTask_WithMinimalRequest_ShouldSucceed() {
        BaglanulyAbatTaskRequest request = BaglanulyAbatTaskRequest.builder()
                .title("Test Task").build();

        when(taskRepository.save(any())).thenReturn(testTask);
        when(commentRepository.countByTaskId(any())).thenReturn(0L);
        when(attachmentRepository.countByTaskId(any())).thenReturn(0L);

        BaglanulyAbatTaskResponse response = taskService.createTask(request);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Test Task");
    }

    @Test
    @DisplayName("getTaskById — not found throws")
    void getTaskById_WhenMissing_ShouldThrow() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(999L))
                .isInstanceOf(BaglanulyAbatResourceNotFoundException.class);
    }

    @Test
    @DisplayName("getAllTasks — returns list")
    void getAllTasks_ShouldReturnList() {
        when(taskRepository.findAll()).thenReturn(List.of(testTask));
        when(commentRepository.countByTaskId(any())).thenReturn(0L);
        when(attachmentRepository.countByTaskId(any())).thenReturn(0L);

        List<BaglanulyAbatTaskResponse> tasks = taskService.getAllTasks();

        assertThat(tasks).hasSize(1);
    }

    @Test
    @DisplayName("updateTaskStatus — changes status")
    void updateTaskStatus_ShouldChangeStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(commentRepository.countByTaskId(any())).thenReturn(0L);
        when(attachmentRepository.countByTaskId(any())).thenReturn(0L);

        BaglanulyAbatTaskResponse response = taskService.updateTaskStatus(1L,
                BaglanulyAbatTaskStatus.IN_PROGRESS);

        assertThat(response.getStatus()).isEqualTo(BaglanulyAbatTaskStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("deleteTask — calls repository delete")
    void deleteTask_ShouldCallDelete() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        taskService.deleteTask(1L);

        verify(taskRepository).delete(testTask);
    }
}