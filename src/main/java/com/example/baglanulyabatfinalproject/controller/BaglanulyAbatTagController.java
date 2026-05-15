package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTagDto.BaglanulyAbatTagRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTagDto.BaglanulyAbatTagResponse;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class BaglanulyAbatTagController {

    private final BaglanulyAbatTagService tagService;

    @PostMapping
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatTagResponse>> create(
            @Valid @RequestBody BaglanulyAbatTagRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaglanulyAbatApiResponse.success("Tag created", tagService.createTag(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatTagResponse>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(tagService.getTagById(id)));
    }

    @GetMapping
    public ResponseEntity<BaglanulyAbatApiResponse<List<BaglanulyAbatTagResponse>>> getAll(
            @RequestParam(required = false) String search) {
        List<BaglanulyAbatTagResponse> tags = (search != null)
                ? tagService.searchTags(search)
                : tagService.getAllTags();
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(tags));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatTagResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody BaglanulyAbatTagRequest request) {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Tag updated",
                tagService.updateTag(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success("Tag deleted", null));
    }
}