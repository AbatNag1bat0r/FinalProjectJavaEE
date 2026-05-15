package com.example.baglanulyabatfinalproject.controller;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatDashboardDto;
import com.example.baglanulyabatfinalproject.service.BaglanulyAbatDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class BaglanulyAbatDashboardController {

    private final BaglanulyAbatDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<BaglanulyAbatApiResponse<BaglanulyAbatDashboardDto>> getDashboard() {
        return ResponseEntity.ok(BaglanulyAbatApiResponse.success(dashboardService.getDashboard()));
    }
}