package com.example.baglanulyabatfinalproject.repository;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaglanulyAbatAttachmentRepository extends JpaRepository<BaglanulyAbatAttachment, Long> {

    List<BaglanulyAbatAttachment> findByTaskId(Long taskId);

    Long countByTaskId(Long taskId);

    void deleteAllByTaskId(Long taskId);
}