package com.example.baglanulyabatfinalproject.dto;

import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask.BaglanulyAbatTaskPriority;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask.BaglanulyAbatTaskStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaglanulyAbatTaskFilterDto {

    private String search;

    private BaglanulyAbatTaskStatus status;

    private BaglanulyAbatTaskPriority priority;

    private Long assigneeId;

    private Long projectId;

    private Long tagId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDateTo;

    // Пагинация
    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    // Сортировка
    @Builder.Default
    private String sortBy = "createdAt";

    @Builder.Default
    private String sortDir = "desc";
}