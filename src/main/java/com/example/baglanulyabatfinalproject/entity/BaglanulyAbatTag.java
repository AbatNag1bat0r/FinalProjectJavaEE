package com.example.baglanulyabatfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "tasks")
@ToString(exclude = "tasks")

public class BaglanulyAbatTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =false, unique = true, length = 50)
    private String name;

    @Column(length = 7)
    private String color;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @Builder.Default
    private List<BaglanulyAbatTask> tasks = new ArrayList<>();
}
