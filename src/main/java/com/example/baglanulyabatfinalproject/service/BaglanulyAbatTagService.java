package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTagDto.BaglanulyAbatTagRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatTagDto.BaglanulyAbatTagResponse;

import java.util.List;

public interface BaglanulyAbatTagService {

    BaglanulyAbatTagResponse createTag(BaglanulyAbatTagRequest request);

    BaglanulyAbatTagResponse getTagById(Long id);

    List<BaglanulyAbatTagResponse> getAllTags();

    List<BaglanulyAbatTagResponse> searchTags(String name);

    BaglanulyAbatTagResponse updateTag(Long id, BaglanulyAbatTagRequest request);

    void deleteTag(Long id);
}