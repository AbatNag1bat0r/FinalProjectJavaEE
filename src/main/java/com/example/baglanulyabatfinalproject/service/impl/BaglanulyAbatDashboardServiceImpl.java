package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatDashboardDto;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskStatus;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatCommentRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatProjectRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatTaskRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatUserRepository;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BaglanulyAbatDashboardServiceImpl implements BaglanulyAbatDashboardService {

    private final BaglanulyAbatTaskRepository taskRepository;
    private final BaglanulyAbatUserRepository userRepository;
    private final BaglanulyAbatProjectRepository projectRepository;
    private final BaglanulyAbatCommentRepository commentRepository;

    @Override
    public BaglanulyAbatDashboardDto getDashboard() {
        log.info("Building dashboard statistics");

        Map<String, Long> tasksByStatus = new LinkedHashMap<>();
        for (BaglanulyAbatTaskStatus status : BaglanulyAbatTaskStatus.values()) {
            tasksByStatus.put(status.name(), (long) taskRepository.findByStatus(status).size());
        }

        Map<String, Long> tasksByPriority = new LinkedHashMap<>();
        for (BaglanulyAbatTaskPriority priority : BaglanulyAbatTaskPriority.values()) {
            tasksByPriority.put(priority.name(), (long) taskRepository.findByPriority(priority).size());
        }

        long overdue = taskRepository
                .findByDueDateBeforeAndStatusNot(LocalDate.now(), BaglanulyAbatTaskStatus.DONE)
                .stream()
                .filter(t -> t.getStatus() != BaglanulyAbatTaskStatus.CANCELLED)
                .count();

        Map<String, Long> topAssignees = new LinkedHashMap<>();
        userRepository.findAllByIsActiveTrue().stream()
                .limit(5)
                .forEach(u -> {
                    long count = taskRepository.countByAssigneeIdAndStatus(
                            u.getId(), BaglanulyAbatTaskStatus.IN_PROGRESS);
                    topAssignees.put(u.getUsername(), count);
                });

        return BaglanulyAbatDashboardDto.builder()
                .totalTasks(taskRepository.count())
                .totalUsers(userRepository.count())
                .totalProjects(projectRepository.count())
                .totalComments(commentRepository.count())
                .tasksByStatus(tasksByStatus)
                .tasksByPriority(tasksByPriority)
                .overdueTasks(overdue)
                .topAssignees(topAssignees)
                .build();
    }
}