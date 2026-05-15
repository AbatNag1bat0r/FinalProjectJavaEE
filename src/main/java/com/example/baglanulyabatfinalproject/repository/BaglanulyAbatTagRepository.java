package com.example.baglanulyabatfinalproject.repository;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaglanulyAbatTagRepository extends JpaRepository<BaglanulyAbatTag, Long> {

    Optional<BaglanulyAbatTag> findByName(String name);

    boolean existsByName(String name);

    List<BaglanulyAbatTag> findByNameContainingIgnoreCase(String name);
}