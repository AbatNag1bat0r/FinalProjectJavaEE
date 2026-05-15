package com.example.baglanulyabatfinalproject.service.impl;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTagDto.BaglanulyAbatTagRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTagDto.BaglanulyAbatTagResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatTag;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatDuplicateResourceException;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatTagRepository;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BaglanulyAbatTagServiceImpl implements BaglanulyAbatTagService {

    private final BaglanulyAbatTagRepository tagRepository;

    @Override
    @Transactional
    public BaglanulyAbatTagResponse createTag(BaglanulyAbatTagRequest request) {
        log.info("Creating tag: {}", request.getName());
        if (tagRepository.existsByName(request.getName())) {
            throw new BaglanulyAbatDuplicateResourceException("Tag already exists: " + request.getName());
        }
        BaglanulyAbatTag tag = BaglanulyAbatTag.builder()
                .name(request.getName())
                .color(request.getColor())
                .build();
        return toResponse(tagRepository.save(tag));
    }

    @Override
    public BaglanulyAbatTagResponse getTagById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public List<BaglanulyAbatTagResponse> getAllTags() {
        return tagRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<BaglanulyAbatTagResponse> searchTags(String name) {
        return tagRepository.findByNameContainingIgnoreCase(name).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public BaglanulyAbatTagResponse updateTag(Long id, BaglanulyAbatTagRequest request) {
        log.info("Updating tag {}", id);
        BaglanulyAbatTag tag = findById(id);
        if (!tag.getName().equals(request.getName()) && tagRepository.existsByName(request.getName())) {
            throw new BaglanulyAbatDuplicateResourceException("Tag name already exists: " + request.getName());
        }
        tag.setName(request.getName());
        tag.setColor(request.getColor());
        return toResponse(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        log.info("Deleting tag {}", id);
        tagRepository.delete(findById(id));
    }

    private BaglanulyAbatTag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new BaglanulyAbatResourceNotFoundException("Tag", id));
    }

    private BaglanulyAbatTagResponse toResponse(BaglanulyAbatTag t) {
        return BaglanulyAbatTagResponse.builder()
                .id(t.getId()).name(t.getName()).color(t.getColor())
                .tasksCount(t.getTasks() != null ? t.getTasks().size() : 0)
                .build();
    }
}