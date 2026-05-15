package com.example.baglanulyabatfinalproject.dto;

import lombok.*;

import java.time.LocalDateTime;

public class BaglanulyAbatAttachmentDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaglanulyAbatAttachmentResponse {

        private Long id;
        private String fileName;
        private String filePath;
        private Long fileSize;
        private String contentType;
        private LocalDateTime uploadedAt;
        private Long taskId;
    }
}