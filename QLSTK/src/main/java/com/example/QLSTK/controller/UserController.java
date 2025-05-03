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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User Management", description = "APIs for admin to manage users and view statistics")
@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Update user information (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{maND}")
    public ResponseEntity<?> updateUser(@PathVariable Integer maND, @RequestBody NguoiDung updatedUser) {
        try {
            UserResponse response = userService.updateUser(maND, updatedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete user (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{maND}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer maND) {
        try {
            userService.deleteUser(maND);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get admin profile (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile")
    public ResponseEntity<?> getAdminProfile(Authentication authentication) {
        try {
            String email = authentication.getName();
            UserResponse response = userService.getUserProfile(email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get statistics (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRevenue", userService.getTotalRevenue());
        stats.put("dailyRevenue", userService.getDailyRevenue());
        stats.put("monthlyRevenue", userService.getMonthlyRevenue());
        stats.put("dailyVisits", userService.getDailyVisits());
        stats.put("allTransactions", userService.getAllTransactions());
        return ResponseEntity.ok(stats);
    }
}