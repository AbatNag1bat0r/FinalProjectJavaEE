package com.example.baglanulyabatfinalproject.repository;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaglanulyAbatUserRepository extends JpaRepository<BaglanulyAbatUser, Long> {

    Optional<BaglanulyAbatUser> findByUsername(String username);

    Optional<BaglanulyAbatUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<BaglanulyAbatUser> findAllByIsActiveTrue();

    @Query("SELECT u FROM BaglanulyAbatUser u WHERE u.isActive = true AND " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<BaglanulyAbatUser> searchUsers(@Param("query") String query);
}