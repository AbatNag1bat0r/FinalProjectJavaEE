package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAttachmentDto.BaglanulyAbatAttachmentResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatAttachment;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTask;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatBadRequestException;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatAttachmentRepository;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatTaskRepository;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatAttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BaglanulyAbatAttachmentServiceImpl implements BaglanulyAbatAttachmentService {

    private final BaglanulyAbatAttachmentRepository attachmentRepository;
    private final BaglanulyAbatTaskRepository taskRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    @Transactional
    public BaglanulyAbatAttachmentResponse uploadAttachment(Long taskId, MultipartFile file) {
        log.info("Uploading file {} for task {}", file.getOriginalFilename(), taskId);

        if (file.isEmpty()) {
            throw new BaglanulyAbatBadRequestException("File cannot be empty");
        }

        BaglanulyAbatTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("Task", taskId));

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            BaglanulyAbatAttachment attachment = BaglanulyAbatAttachment.builder()
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath.toString())
                    .fileSize(file.getSize())
                    .contentType(file.getContentType())
                    .task(task)
                    .build();

            return toResponse(attachmentRepository.save(attachment));

        } catch (IOException e) {
            log.error("Failed to upload file: {}", e.getMessage());
            throw new BaglanulyAbatBadRequestException("Failed to upload file: " + e.getMessage());
        }
    }

    @Override
    public List<BaglanulyAbatAttachmentResponse> getAttachmentsByTask(Long taskId) {
        return attachmentRepository.findByTaskId(taskId).stream().map(this::toResponse).toList();
    }

    @Override
    public BaglanulyAbatAttachmentResponse getAttachmentById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    @Transactional
    public void deleteAttachment(Long id) {
        log.info("Deleting attachment {}", id);
        BaglanulyAbatAttachment attachment = findById(id);

        // Удаляем физический файл
        try {
            Files.deleteIfExists(Paths.get(attachment.getFilePath()));
        } catch (IOException e) {
            log.warn("Could not delete physical file: {}", attachment.getFilePath());
        }

        attachmentRepository.delete(attachment);
    }

    private BaglanulyAbatAttachment findById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("Attachment", id));
    }

    private BaglanulyAbatAttachmentResponse toResponse(BaglanulyAbatAttachment a) {
        return BaglanulyAbatAttachmentResponse.builder()
                .id(a.getId()).fileName(a.getFileName()).filePath(a.getFilePath())
                .fileSize(a.getFileSize()).contentType(a.getContentType())
                .uploadedAt(a.getUploadedAt()).taskId(a.getTask().getId())
                .build();
    }
}