package com.example.baglanulyabatfinalproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BaglanulyAbatNotificationService {

    @Async("baglanulyAbatTaskExecutor")
    public void notifyTaskAssigned(Long taskId, String assigneeUsername) {
        log.info("[ASYNC] Notifying user '{}' about new task assignment (taskId={})",
                assigneeUsername, taskId);
        simulateWork("task-assigned notification");
    }

    @Async("baglanulyAbatTaskExecutor")
    public void notifyTaskStatusChanged(Long taskId, String newStatus, String assigneeUsername) {
        log.info("[ASYNC] Notifying '{}': task {} status changed to {}",
                assigneeUsername, taskId, newStatus);
        simulateWork("status-change notification");
    }

    @Async("baglanulyAbatTaskExecutor")
    public void notifyNewComment(Long taskId, String authorUsername, String assigneeUsername) {
        log.info("[ASYNC] Notifying '{}': new comment by '{}' on task {}",
                assigneeUsername, authorUsername, taskId);
        simulateWork("new-comment notification");
    }

    @Async("baglanulyAbatTaskExecutor")
    public void generateProjectReport(Long projectId) {
        log.info("[ASYNC] Generating report for project {}", projectId);
        simulateWork("report generation");
        log.info("[ASYNC] Report for project {} completed", projectId);
    }

    private void simulateWork(String task) {
        try {
            Thread.sleep(100); // имитация I/O
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Async task '{}' interrupted", task);
        }
    }
}