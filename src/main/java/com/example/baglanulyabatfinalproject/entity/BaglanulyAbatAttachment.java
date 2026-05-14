package com.example.baglanulyabatfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "task")
@ToString(exclude = "task")

public class BaglanulyAbatAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fillName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "content_type", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private BaglanulyAbatTask task;
}
