package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatAttachmentDto.BaglanulyAbatAttachmentResponse;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
public class BaglanulyAbatAttachmentController {

    private final BaglanulyAbatAttachmentService attachmentService;

    @PostMapping(value = "/task/{taskId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatAttachmentResponse>> upload(
            @PathVariable Long taskId,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaglanulyAbatApiResponse.success("File uploaded",
                        attachmentService.uploadAttachment(taskId, file)));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatAttachmentResponse>>> getByTask(
            @PathVariable Long taskId) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(
                attachmentService.getAttachmentsByTask(taskId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatAttachmentResponse>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(
                attachmentService.getAttachmentById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> delete(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Attachment deleted", null));
    }
}