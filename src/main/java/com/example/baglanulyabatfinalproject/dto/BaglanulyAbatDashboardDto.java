package com.example.baglanulyabatfinalproject.dto;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaglanulyAbatDashboardDto {

    private long totalTasks;
    private long totalUsers;
    private long totalProjects;
    private long totalComments;

    private Map<String, Long> tasksByStatus;

    private Map<String, Long> tasksByPriority;

    private long overdueTasks;

    private Map<String, Long> topAssignees;
}