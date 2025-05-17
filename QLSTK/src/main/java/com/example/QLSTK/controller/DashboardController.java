package com.example.QLSTK.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.QLSTK.dto.DashboardSummaryDTO;
import com.example.QLSTK.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary(Authentication authentication) {
        String email = authentication.getName(); // assuming email is used as username
        return dashboardService.getSummary(email);
    }
}
