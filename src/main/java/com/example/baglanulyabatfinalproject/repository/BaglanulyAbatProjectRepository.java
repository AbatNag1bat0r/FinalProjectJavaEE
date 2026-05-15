package com.example.baglanulyabatfinalproject.repository;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatProject;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaglanulyAbatProjectRepository extends JpaRepository<BaglanulyAbatProject, Long> {

    List<BaglanulyAbatProject> findByStatus(BaglanulyAbatProjectStatus status);

    @Query("SELECT p FROM BaglanulyAbatProject p JOIN p.members m WHERE m.id = :userId")
    List<BaglanulyAbatProject> findProjectsByMemberId(@Param("userId") Long userId);

    boolean existsByName(String name);
}