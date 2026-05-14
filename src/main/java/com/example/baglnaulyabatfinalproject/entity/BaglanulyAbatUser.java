package com.example.baglnaulyabatfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"task", "comments", "projects"})
@ToString(exclude = {"tasks", "comments", "projects"})

public class BaglanulyAbatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", length = 50)
    private String fisrtName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @Builder.Default
    private BaglanulyAbatUserRole role = BaglanulyAbatUserRole.USER;

    @Column(name ="created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BaglanulyAbatTask> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BaglnaulyAbatComments> comments = new ArrayList<>();

    @ManyToMany(mapperBy ="members", fetch = FetchType.Lazy)
    @Builder.Default
    private List<BaglnaulyAbatProject> projects = new ArrayList<>();

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public enum BaglnaulyAbatUserRole{
        AMIN, MANAGER, USER
    }
}
