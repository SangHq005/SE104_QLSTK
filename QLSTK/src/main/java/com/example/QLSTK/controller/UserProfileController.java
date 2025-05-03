package com.example.QLSTK.controller;

import com.example.QLSTK.dto.UserResponse;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "User Profile and Dashboard", description = "APIs for users to view profile and dashboard")
@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get user profile (User only)")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        try {
            String email = authentication.getName();
            UserResponse response = userService.getUserProfile(email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update user profile (User only)")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody NguoiDung updatedUser, Authentication authentication) {
        try {
            Integer userId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            UserResponse response = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get user dashboard (User only)")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/dashboard")
    public ResponseEntity<?> getUserDashboard(Authentication authentication) {
        try {
            Integer userId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            Map<String, Object> dashboard = userService.getUserDashboard(userId);
            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}