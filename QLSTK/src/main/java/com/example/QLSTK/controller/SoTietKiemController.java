package com.example.QLSTK.controller;

import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.entity.SoTietKiem;
import com.example.QLSTK.service.SoTietKiemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Savings Type Management", description = "APIs for admin to manage savings types")
@RestController
@RequestMapping("/api/admin/sotietkiem")
public class SoTietKiemController {
    @Autowired
    private SoTietKiemService soTietKiemService;

    @Operation(summary = "Get all savings types")
    @GetMapping
    public ResponseEntity<List<SoTietKiem>> getAllSoTietKiem() {
        return ResponseEntity.ok(soTietKiemService.getAllSoTietKiem());
    }

    @Operation(summary = "Create a new savings type (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createSoTietKiem(@RequestBody SoTietKiem soTietKiem, Authentication authentication) {
        try {
            Integer adminId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            return ResponseEntity.status(HttpStatus.CREATED).body(soTietKiemService.createSoTietKiem(soTietKiem, adminId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update a savings type (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSoTietKiem(@PathVariable Integer id, @RequestBody SoTietKiem soTietKiem, Authentication authentication) {
        try {
            Integer adminId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            return ResponseEntity.ok(soTietKiemService.updateSoTietKiem(id, soTietKiem, adminId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a savings type (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSoTietKiem(@PathVariable Integer id, Authentication authentication) {
        try {
            Integer adminId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            soTietKiemService.deleteSoTietKiem(id, adminId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}