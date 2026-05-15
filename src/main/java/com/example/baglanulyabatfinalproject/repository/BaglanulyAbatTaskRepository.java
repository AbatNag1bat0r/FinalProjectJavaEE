package com.example.baglanulyabatfinalproject.repository;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BaglanulyAbatTaskRepository
        extends JpaRepository<BaglanulyAbatTask, Long>,
        JpaSpecificationExecutor<BaglanulyAbatTask> {

    List<BaglanulyAbatTask> findByAssigneeId(Long assigneeId);

    List<BaglanulyAbatTask> findByProjectId(Long projectId);

    List<BaglanulyAbatTask> findByStatus(BaglanulyAbatTaskStatus status);

    List<BaglanulyAbatTask> findByPriority(BaglanulyAbatTaskPriority priority);

    List<BaglanulyAbatTask> findByAssigneeIdAndStatus(Long assigneeId, BaglanulyAbatTaskStatus status);

    List<BaglanulyAbatTask> findByDueDateBeforeAndStatusNot(LocalDate date, BaglanulyAbatTaskStatus status);

    @Query("SELECT t FROM BaglanulyAbatTask t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<BaglanulyAbatTask> searchTasks(@Param("query") String query);

    @Query("SELECT COUNT(t) FROM BaglanulyAbatTask t WHERE t.assignee.id = :userId AND t.status = :status")
    Long countByAssigneeIdAndStatus(@Param("userId") Long userId,
                                    @Param("status") BaglanulyAbatTaskStatus status);
}