package com.example.baglanulyabatfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"task", "author"})
@ToString(exclude = {"task", "author"})
public class BaglanulyAbatComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name ="created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_edited", nullable = false)
    @Builder.Default
    private Boolean isEdited = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private BaglanulyAbatTask task;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private BaglanulyAbatUser author;

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
        this.isEdited = true;
    }
}
