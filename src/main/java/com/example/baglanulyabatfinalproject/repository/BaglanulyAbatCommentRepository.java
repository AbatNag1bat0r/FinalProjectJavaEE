package com.example.baglanulyabatfinalproject.repository;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaglanulyAbatCommentRepository extends JpaRepository<BaglanulyAbatComment, Long> {

    List<BaglanulyAbatComment> findByTaskIdOrderByCreatedAtDesc(Long taskId);

    List<BaglanulyAbatComment> findByAuthorId(Long authorId);

    Long countByTaskId(Long taskId);
}