package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAttachmentDto.BaglanulyAbatAttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BaglanulyAbatAttachmentService {

    BaglanulyAbatAttachmentResponse uploadAttachment(Long taskId, MultipartFile file);

    List<BaglanulyAbatAttachmentResponse> getAttachmentsByTask(Long taskId);

    BaglanulyAbatAttachmentResponse getAttachmentById(Long id);

    void deleteAttachment(Long id);
}